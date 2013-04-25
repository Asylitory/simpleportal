package simpleportal.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import simpleportal.logic.ContentManager;
import simpleportal.logic.basic_classes.Article;
import simpleportal.logic.basic_classes.ArticleWrap;
import simpleportal.logic.basic_classes.Comment;
import simpleportal.logic.basic_classes.User;
import simpleportal.web.forms.ArticleEditForm;
import simpleportal.web.forms.ArticleForm;
import simpleportal.web.forms.ArticleListForm;
import simpleportal.web.forms.CommentEditForm;
import simpleportal.web.forms.InfoForm;
import simpleportal.web.forms.LoginInfoForm;
import simpleportal.web.forms.UserEditForm;
import simpleportal.web.forms.UserListForm;
import simpleportal.web.forms.UserProfileForm;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Action constants
	private final int aMESSAGE = 0;
	private final int aEDITUSER = 1;
	private final int aPROFILEUSER = 2;
	private final int aUSERLIST = 3;
	private final int aEDITARTICLE = 4;
	private final int aARTICLE = 5;
	private final int aARTICLELIST = 6;
	private final int aEDITCOMMENT = 7;
	
	//Uset actions
	private final int uNOACTION = 100;
	private final int uREGISTER = 101;
	private final int uUSERCONFIRM = 102;
	private final int uUSERCANCEL = 103;
	private final int uLOGIN = 104;
	private final int uQUIT = 105;
	private final int uPROFILE = 106;
	private final int uUSERPROFILE = 107;
	private final int uPROFILEEDIT = 108;
	private final int uUSERLIST = 109;
	private final int uARTICLELIST = 110;
	private final int uARTICLE = 111;
	private final int uARTICLENEW = 112;
	private final int uARTICLECONFIRM = 113;
	private final int uARTICLECANCEL = 114;
	private final int uARTICLEEDIT = 115;
	private final int uCOMMENTNEW = 116;
	private final int uCOMMENTEDIT = 117;
	private final int uCOMMENTCONFIRM = 118;
	private final int uCOMMENTCANCEL = 119;
	
	private static ContentManager manager = ContentManager.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		int id = -1;
		String hash = new String();
		
		int activeUserId = -1;
		int activeArticleId = -1;
		int activeCommentId = -1;
		
		int action = 0;
		
		InfoForm infoForm = null;
		
		try {
			req.setCharacterEncoding("UTF-8");
			
			Cookie[] cookies = req.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("id")) {
						id = Integer.parseInt(cookie.getValue());
					}
					if (cookie.getName().equals("hash")) {
						hash = cookie.getValue();
					}
					if (cookie.getName().equals("activeUserId")) {
						activeUserId = Integer.parseInt(cookie.getValue());
					}
					if (cookie.getName().equals("activeArticleId")) {
						activeArticleId = Integer.parseInt(cookie.getValue());
					}
					if (cookie.getName().equals("activeCommentId")) {
						activeCommentId = Integer.parseInt(cookie.getValue());
					}
				}
			}
			action = checkAction(req);
			infoForm = manager.checkCookies(id, hash);
			
			if (infoForm.isLogged()) {
				if (uQUIT == action) {
					Cookie cookie;
					
					cookie = new Cookie("id", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					
					cookie = new Cookie("hash", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					
					cookie = new Cookie("activeUserId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					
					cookie = new Cookie("activeArticleId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					
					cookie = new Cookie("activeCommentId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
					
					infoForm.setLogged(false);
					infoForm.setAction(aMESSAGE);
					infoForm.setActionMessage("Вы успешно вышли");
					infoForm.setTitle("Главная страница портала");
					req.setAttribute("form", infoForm);
				}
				if (uPROFILE == action) {
					UserProfileForm userProfileForm = manager.getUserProfile(id);
					userProfileForm.setInfo(infoForm);
					userProfileForm.setAction(aPROFILEUSER);
					userProfileForm.setTitle("Ваш профиль");
					
					Cookie cookie = new Cookie("activeUserId", String.valueOf(id));
					resp.addCookie(cookie);
					
					req.setAttribute("form", userProfileForm);
				}
				if (uUSERPROFILE == action) {
					int userId = Integer.parseInt(req.getParameter("USER"));
					UserProfileForm userProfileForm = manager.getUserProfile(userId);
					
					if (null != userProfileForm) {
						userProfileForm.setInfo(infoForm);
						userProfileForm.setAction(aPROFILEUSER);
						userProfileForm.setTitle("Профиль пользователя " + userProfileForm.getProfileUser().getNickname());
						
						Cookie cookie = new Cookie("activeUserId", String.valueOf(userId));
						resp.addCookie(cookie);
						
						req.setAttribute("form", userProfileForm);
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Пользователь не найден");
						infoForm.setTitle("Ошибка");
						
						req.setAttribute("form", infoForm);
					}
				}
				if (uPROFILEEDIT == action) {
					User user = manager.getUserById(activeUserId);
					if (null != user) {
						if (id == user.getId() || (1 == infoForm.getUserRole() && 1 != user.getRole())) {
							Cookie cookie = new Cookie("activeUserId", String.valueOf(user.getId()));
							resp.addCookie(cookie);
							
							UserEditForm userEditForm = new UserEditForm();
							userEditForm.setEditedUser(user);
							userEditForm.setInfo(infoForm);
							userEditForm.setAction(aEDITUSER);
							userEditForm.setTitle("Редактирование профиля пользователя " + user.getNickname());
							userEditForm.setRoles(manager.getRoles(false));
							req.setAttribute("form", userEditForm);
						} else {
							infoForm.setAction(aMESSAGE);
							infoForm.setActionMessage("Недостаточно прав для выполнения данного дейсвтия");
							infoForm.setTitle("Ошибка");
							req.setAttribute("form", infoForm);
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Произошла внутренняя ошибка");
						infoForm.setTitle("Ошибка");
						req.setAttribute("form", infoForm);
					}
				}
				if (uUSERCONFIRM == action) {
					if (0 != activeUserId) {
						String userPassword = new String();
						String userPasswordRepeat = new String();
						String userNickname = new String();
						int userGroup = -1;
						
						User user = manager.getUserById(activeUserId);
						
						List<String> errors = null;
						
						if (null != req.getParameter("newUserPassword")) {
							userPassword = req.getParameter("newUserPassword");
						}
						if (null != req.getParameter("newUserPasswordRepeat")) {
							userPasswordRepeat = req.getParameter("newUserPasswordRepeat");
						}
						if (null != req.getParameter("newUserNickname")) {
							userNickname = req.getParameter("newUserNickname");
						}
						if (null != req.getParameter("newUserGroup")) {
							userGroup = Integer.parseInt(req.getParameter("newUserGroup"));
						}
						
						if (0 < userNickname.length()) {
							errors = manager.validateUserNickname(userNickname);
						}
						
						if (null == errors) {
							errors = new ArrayList<String>();
						}
						
						if (0 < userPassword.length()) {
							if(!manager.isPasswordValid(userPassword)) {
								errors.add("Пароль может содержать только цифры и символы английского языка");
							}
							if (!userPassword.equals(userPasswordRepeat)) {
								errors.add("Пароль и подтверждение должны совпадать");
							}
						}
											
						if (0 < errors.size()) {
							Cookie cookie = new Cookie("activeUserId", String.valueOf(user.getId()));
							resp.addCookie(cookie);
							
							UserEditForm userEditForm = new UserEditForm();
							userEditForm.setEditedUser(user);
							userEditForm.setInfo(infoForm);
							userEditForm.setAction(aEDITUSER);
							userEditForm.setTitle("Редактирование профиля пользователя " + user.getNickname());
							userEditForm.setRoles(manager.getRoles(false));
							userEditForm.setErrors(errors);
							req.setAttribute("form", userEditForm);
						} else {
							if (0 < userNickname.length()) {
								user.setNickname(userNickname);
							}
							if (1 < userGroup) {
								user.setRole(userGroup);
							}
							manager.updateUser(user);
							if (0 < userPassword.length()) {
								manager.updateUserPassword(userPassword, user.getId());
							}
							resp.sendRedirect("index?USER=" + user.getId());
							return;
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Возникла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uUSERCANCEL == action) {
					action = uNOACTION;
					
					Cookie cookie = new Cookie("activeUserId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				if (uUSERLIST == action) {
					UserListForm userListForm = new UserListForm();
					
					userListForm.setUsersList(manager.getAllUsers());
					userListForm.setRolesList(manager.getRoles(true));
					
					userListForm.setInfo(infoForm);
					userListForm.setAction(aUSERLIST);
					userListForm.setTitle("Список пользователей");
					req.setAttribute("form", userListForm);
				}
				if (uARTICLELIST == action) {
					int sectionId = 0;
					
					if (null != req.getParameter("articlesGroup")) {
						sectionId = Integer.parseInt(req.getParameter("articlesGroup"));
					}
					
					ArticleListForm articleListForm = new ArticleListForm();
					articleListForm.setArticlesList(manager.getAllArticles(sectionId));
					articleListForm.setInfo(infoForm);
					articleListForm.setAction(aARTICLELIST);
					articleListForm.setTitle("Список статей");
					req.setAttribute("form", articleListForm);
				}
				if (uARTICLE == action) {
					int articleId = -1;
					articleId = Integer.parseInt(req.getParameter("ARTICLE"));
					
					Article article = manager.getArticleById(articleId);
					if (null != article) {
						Cookie cookie = new Cookie("activeArticleId", String.valueOf(articleId));
						resp.addCookie(cookie);
						
						ArticleWrap articleWrap = new ArticleWrap(article);
						articleWrap.setAuthorNickname(manager.getUserById(article.getAuthorId()).getNickname());
						articleWrap.setEditorNickname(manager.getUserById(article.getEditorId()).getNickname());
						articleWrap.setSectionName(manager.getSectionById(article.getSection()).getName());
						
						ArticleForm articleForm = new ArticleForm();
						articleForm.setInfo(infoForm);
						articleForm.setAction(aARTICLE);
						articleForm.setArticleWrap(articleWrap);						
						articleForm.setCommentWrapList(manager.getCommentsFromArticle(articleId));
						articleForm.setTitle("Просмотр статьи");
						req.setAttribute("form", articleForm);
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Возникла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uARTICLENEW == action) {
					Article article = new Article();
					article.setId(0);
					
					Cookie cookie = new Cookie("activeArticleId", String.valueOf(0));
					resp.addCookie(cookie);
					
					ArticleEditForm articleEditForm = new ArticleEditForm();
					articleEditForm.setArticle(article);
					articleEditForm.setInfo(infoForm);
					articleEditForm.setAction(aEDITARTICLE);
					articleEditForm.setTitle("Создание новой статьи");
					req.setAttribute("form", articleEditForm);
				}
				if (uARTICLECONFIRM == action) {
					String articleTitle = new String();
					String articleContent = new String();
					int articleSection = 1;
					Article article = null;
					
					User user = manager.getUserById(id);
					
					List<String> errors;
					
					if (0 != activeArticleId) {
						article = manager.getArticleById(activeArticleId);
					} else {
						article = new Article();
						article.setId(0);
					}
					
					if (null != article) {
						if (0 == activeArticleId || 1 == user.getRole() || 2 == user.getRole() || user.getId() == article.getAuthorId()) {
							if (null != req.getParameter("articleTitle")) {
								articleTitle = req.getParameter("articleTitle");
							}
							if (null != req.getParameter("articleContent")) {
								articleContent = req.getParameter("articleContent");
							}
							if (null != req.getParameter("articleSection")) {
								articleSection = Integer.valueOf(req.getParameter("articleSection"));
							}
							
							article.setTitle(articleTitle);
							article.setContent(articleContent);
							article.setSection(articleSection);
							
							errors = manager.validateArticle(article);
							
							if (0 < errors.size()) {
								Cookie cookie = new Cookie("activeArticleId", String.valueOf(article.getId()));
								resp.addCookie(cookie);
								
								ArticleEditForm articleEditForm = new ArticleEditForm();
								articleEditForm.setInfo(infoForm);
								articleEditForm.setAction(aEDITARTICLE);
								articleEditForm.setTitle("Редактирование статьи");
								articleEditForm.setArticle(article);
								articleEditForm.setErrors(errors);
								req.setAttribute("form", articleEditForm);
							} else {
								Date date = new Date();
								
								if (0 == article.getId()) {
									article.setCreateDate(date);
									article.setAuthorId(id);
								}
								article.setEditDate(date);
								article.setEditorId(id);
								
								if (0 == article.getId()) {
									manager.insertArticle(article);
									infoForm.setActionMessage("Статья добавлена в базу");
								} else {
									manager.updateArticle(article);
									infoForm.setActionMessage("Статья изменена");
								}
								
								infoForm.setAction(aMESSAGE);
								infoForm.setTitle("Главная страница портала");
								req.setAttribute("form", infoForm);
							}							
						} else {
							Cookie cookie = new Cookie("activeArticleId", "");
							cookie.setMaxAge(0);
							resp.addCookie(cookie);
							
							infoForm.setAction(aMESSAGE);
							infoForm.setActionMessage("У вас нет прав для данного действия");
							infoForm.setTitle("Главная страница портала");
							req.setAttribute("form", infoForm);					
						}
					} else {
						Cookie cookie = new Cookie("activeArticleId", "");
						cookie.setMaxAge(0);
						resp.addCookie(cookie);
						
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Произошла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uARTICLECANCEL == action) {
					action = uNOACTION;
					
					Cookie cookie = new Cookie("activeArticleId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				if (uARTICLEEDIT == action) {
					Article article = manager.getArticleById(activeArticleId);
					
					if (null != article) {
						User user = manager.getUserById(id);
						if (1 == user.getRole() || 2 == user.getRole() || id == article.getAuthorId()) {
							ArticleEditForm articleEditForm = new ArticleEditForm();
							articleEditForm.setArticle(article);
							articleEditForm.setInfo(infoForm);
							articleEditForm.setAction(aEDITARTICLE);
							articleEditForm.setTitle("Редактирование статьи");
							req.setAttribute("form", articleEditForm);
						} else {
							infoForm.setAction(aMESSAGE);
							infoForm.setActionMessage("У вас недостаточно прав для данного действия");
							infoForm.setTitle("Главная страница портала");
							req.setAttribute("form", infoForm);
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Произошла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uCOMMENTNEW == action) {
					Cookie cookie = new Cookie("activeCommentId", String.valueOf(0));
					resp.addCookie(cookie);
					
					Comment comment = new Comment();
					comment.setId(0);
					
					CommentEditForm commentEditForm = new CommentEditForm();
					commentEditForm.setComment(comment);
					commentEditForm.setInfo(infoForm);
					commentEditForm.setAction(aEDITCOMMENT);
					commentEditForm.setTitle("Редактирование комментария");
					commentEditForm.setError(false);
					req.setAttribute("form", commentEditForm);
				}
				if (uCOMMENTEDIT == action) {
					User user = manager.getUserById(id);
					
					int commentId = 0;
					commentId = Integer.parseInt(req.getParameter("COMMENT_ID"));
					
					Comment comment = manager.getCommentById(commentId);
					
					if (null != comment) {
						if (1 == user.getRole() || 2 == user.getRole() || user.getId() == comment.getAuthorId()) {
							Cookie cookie = new Cookie("activeCommentId", String.valueOf(commentId));
							resp.addCookie(cookie);
							
							CommentEditForm commentEditForm = new CommentEditForm();
							commentEditForm.setComment(comment);
							commentEditForm.setInfo(infoForm);
							commentEditForm.setAction(aEDITCOMMENT);
							commentEditForm.setTitle("Редактирование комментария");
							commentEditForm.setError(false);
							req.setAttribute("form", commentEditForm);
						} else {
							infoForm.setAction(aMESSAGE);
							infoForm.setActionMessage("У вас нет прав для данного действия");
							infoForm.setTitle("Главная страница портала");
							req.setAttribute("form", infoForm);
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Произошла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uCOMMENTCONFIRM == action) {
					String content = new String();
					
					if (null != req.getParameter("commentContent")) {
						content = req.getParameter("commentContent");
					}
					
					User user = manager.getUserById(id);
					
					Comment comment = null;
					
					if (0 == activeCommentId) {
						comment = new Comment();
						comment.setId(0);
					} else {
						comment = manager.getCommentById(activeCommentId);
					}
					
					if (null != comment) {
						if (0 == activeCommentId || 1 == user.getRole() || 2 == user.getRole() || user.getId() == comment.getAuthorId()) {
							comment.setContent(content);
							
							if (manager.validateComment(comment)) {
								Date date = new Date();
								if (0 == activeCommentId) {
									comment.setAuthorId(id);
									comment.setCreateDate(date);
								}
								comment.setEditorId(id);
								comment.setEditDate(date);
								comment.setArticleId(activeArticleId);
								if (0 == activeCommentId) {
									manager.insertComment(comment);
								} else {
									manager.updateComment(comment);
								}
								resp.sendRedirect("index?ARTICLE=" + activeArticleId);
								return;
							} else {
								Cookie cookie = new Cookie("activeCommentId", String.valueOf(comment.getId()));
								resp.addCookie(cookie);
								
								CommentEditForm commentEditForm = new CommentEditForm();
								commentEditForm.setComment(comment);
								commentEditForm.setInfo(infoForm);
								commentEditForm.setAction(aEDITCOMMENT);
								commentEditForm.setTitle("Редактирование комментария");
								commentEditForm.setError(true);
								req.setAttribute("form", commentEditForm);
							}
						} else {
							infoForm.setAction(aMESSAGE);
							infoForm.setActionMessage("У вас недостаточно прав для данного действия");
							infoForm.setTitle("Главная страница портала");
							req.setAttribute("form", infoForm);
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Произошла внутренняя ошибка");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uCOMMENTCANCEL == action) {
					resp.sendRedirect("index?ARTICLE=" + activeArticleId);
					return;
				}
				if (uNOACTION == action) {
					infoForm.setAction(aMESSAGE);
					infoForm.setActionMessage("Добро пожаловать на портал");
					infoForm.setTitle("Главная страница портала");
					req.setAttribute("form", infoForm);
				}
			} else {
				if (uREGISTER == action) {
					User user = new User();
					user.setId(0);
					
					Cookie cookie = new Cookie("activeUserId", String.valueOf(0));
					resp.addCookie(cookie);
					
					UserEditForm userEditForm = new UserEditForm();
					userEditForm.setEditedUser(user);
					userEditForm.setInfo(infoForm);
					userEditForm.setAction(aEDITUSER);
					userEditForm.setTitle("Регистрация нового пользователя");
					req.setAttribute("form", userEditForm);
				}
				if (uUSERCONFIRM == action) {
					if (0 == activeUserId) {
						String userLogin = new String();
						String userPassword = new String();
						String userPasswordRepeat = new String();
						String userNickname = new String();
						List<String> errors = null;
						
						if (null != req.getParameter("userLogin")) {
							userLogin = req.getParameter("userLogin");
						}
						if (null != req.getParameter("userPassword")) {
							userPassword = req.getParameter("userPassword");
						}
						if (null != req.getParameter("userPasswordRepeat")) {
							userPasswordRepeat = req.getParameter("userPasswordRepeat");
						}
						if (null != req.getParameter("userNickname")) {
							userNickname = req.getParameter("userNickname");
						}
						
						errors = manager.validateUserData(userLogin, userNickname);
						
						if (!manager.isPasswordValid(userPassword)) {
							errors.add("Пароль может содержать только цифры и символы английского языка");
						} else {
							if (!userPassword.equals(userPasswordRepeat)) {
								errors.add("Пароль и подтверждение должны совпадать");
							}
						}
						
						if (0 < errors.size()) {
							User user = new User();
							user.setId(0);
							
							Cookie cookie = new Cookie("activeUserId", String.valueOf(0));
							resp.addCookie(cookie);
							
							UserEditForm userEditForm = new UserEditForm();
							userEditForm.setEditedUser(user);
							userEditForm.setInfo(infoForm);
							userEditForm.setAction(aEDITUSER);
							userEditForm.setTitle("Регистрация нового пользователя");
							userEditForm.setErrors(errors);
							req.setAttribute("form", userEditForm);
						} else {
							User user = new User();
							user.setNickname(userNickname);
							user.setJoinDate(new Date());
							user.setRole(4);
							
							manager.insertUser(userLogin, userPassword, user);
							
							resp.sendRedirect("index");
							return;
						}
					} else {
						infoForm.setAction(aMESSAGE);
						infoForm.setActionMessage("Недостаточно прав для данного действия");
						infoForm.setTitle("Главная страница портала");
						req.setAttribute("form", infoForm);
					}
				}
				if (uUSERCANCEL == action) {
					action = uNOACTION;
					
					Cookie cookie = new Cookie("activeUserId", "");
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
				if (uLOGIN == action) {
					String userLogin = new String();
					String userPassword = new String();
					
					if (null != req.getParameter("login")) {
						userLogin = req.getParameter("login");
					}
					if (null != req.getParameter("password")) {
						userPassword = req.getParameter("password");
					}
					
					LoginInfoForm loginInfoForm = manager.tryLogin(userLogin, userPassword);
					
					if (loginInfoForm.isLogged()) {
						for (Cookie cookie : loginInfoForm.getCookies()) {
							resp.addCookie(cookie);
						}
						
						loginInfoForm.setAction(aMESSAGE);
						loginInfoForm.setActionMessage("Вы успешно залогинились");
						loginInfoForm.setTitle("Главная страница портала");
						req.setAttribute("form", loginInfoForm);
					} else {
						loginInfoForm.setAction(aMESSAGE);
						loginInfoForm.setActionMessage("Неверный логин или пароль");
						loginInfoForm.setTitle("Главная страница портала");
						req.setAttribute("form", loginInfoForm);
					}					
				}
				if (uNOACTION == action) {
					infoForm.setAction(aMESSAGE);
					infoForm.setActionMessage("Для работы с порталом требуется залогиниться " +
							"или зарегистрироваться");
					infoForm.setTitle("Главная страница портала");
					req.setAttribute("form", infoForm);
				}
			}
			getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
			return;
		} catch (SQLException sqlException) {
			throw new IOException(sqlException.getMessage());
		} finally {}
	}
	
	private int checkAction(HttpServletRequest req) {
		if (null != req.getParameter("uREGISTER")) {
			return uREGISTER;
		}
		if (null != req.getParameter("uUSERCONFIRM")) {
			return uUSERCONFIRM;
		}
		if (null != req.getParameter("uUSERCANCEL")) {
			return uUSERCANCEL;
		}
		if (null != req.getParameter("uLOGIN")) {
			return uLOGIN;
		}
		if (null != req.getParameter("uQUIT")) {
			return uQUIT;
		}
		if (null != req.getParameter("uPROFILE")) {
			return uPROFILE;
		}
		if (null != req.getParameter("USER")) {
			return uUSERPROFILE;
		}
		if (null != req.getParameter("uPROFILEEDIT")) {
			return uPROFILEEDIT;
		}
		if (null != req.getParameter("uUSERLIST")) {
			return uUSERLIST;
		}
		if (null != req.getParameter("uARTICLELIST")) {
			return uARTICLELIST;
		}
		if (null != req.getParameter("ARTICLE")) {
			return uARTICLE;
		}
		if (null != req.getParameter("uARTICLENEW")) {
			return uARTICLENEW;
		}
		if (null != req.getParameter("uARTICLECONFIRM")) {
			return uARTICLECONFIRM;
		}
		if (null != req.getParameter("uARTICLECANCEL")) {
			return uARTICLECANCEL;
		} 
		if (null != req.getParameter("uARTICLEEDIT")) {
			return uARTICLEEDIT;
		}
		if (null != req.getParameter("uCOMMENTNEW")) {
			return uCOMMENTNEW;
		}
		if (null != req.getParameter("uCOMMENTEDIT")) {
			return uCOMMENTEDIT;
		}
		if (null != req.getParameter("uCOMMENTCONFIRM")) {
			return uCOMMENTCONFIRM;
		}
		if (null != req.getParameter("uCOMMENTCANCEL")) {
			return uCOMMENTCANCEL;
		}
		return uNOACTION;
	}
}
