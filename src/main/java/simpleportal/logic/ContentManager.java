package simpleportal.logic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import simpleportal.logic.basic_classes.Article;
import simpleportal.logic.basic_classes.ArticleWrap;
import simpleportal.logic.basic_classes.Comment;
import simpleportal.logic.basic_classes.CommentWrap;
import simpleportal.logic.basic_classes.Role;
import simpleportal.logic.basic_classes.Section;
import simpleportal.logic.basic_classes.User;
import simpleportal.web.forms.InfoForm;
import simpleportal.web.forms.LoginInfoForm;
import simpleportal.web.forms.UserProfileForm;

public class ContentManager {
	private static DBConnect dbConnect;
	private static ContentManager instance;
		
	private ContentManager() {}
	
	public static synchronized ContentManager getInstance() {
		if (null == instance) {
			dbConnect = DBConnect.getInstance();
			instance = new ContentManager();
		}
		return instance;
	}
	
	public String getMD5Hash(String input) {
		String md5 = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	public InfoForm checkCookies(int id, String hash) throws SQLException {
		InfoForm infoForm = new InfoForm();
		
		String passwordHash = dbConnect.getPasswordHashById(id);
		
		if (null != passwordHash && hash.equals(getMD5Hash(passwordHash))) {
			User user = dbConnect.getUserById(id);
			
			infoForm.setUserRole(user.getRole());
			infoForm.setUserId(user.getId());
			infoForm.setNickname(user.getNickname());
			infoForm.setSectionsList(dbConnect.getAllSections());
			infoForm.setLogged(true);
		} else {
			infoForm.setLogged(false);
		}
		
		return infoForm;
	}
	
	public LoginInfoForm tryLogin(String login, String password) throws SQLException {
		LoginInfoForm loginInfoForm = new LoginInfoForm();
		String hash = getMD5Hash(password);
		String passwordHash = dbConnect.getPasswordHashByLogin(login);
		
		if (null != passwordHash && hash.equals(passwordHash)) {
			User user = dbConnect.getUserByLogin(login);
			
			Cookie cookie;
			
			cookie = new Cookie("id", String.valueOf(user.getId()));
			loginInfoForm.addCookie(cookie);
			
			cookie = new Cookie("hash", getMD5Hash(passwordHash));
			loginInfoForm.addCookie(cookie);
			
			loginInfoForm.setUserRole(user.getRole());
			loginInfoForm.setUserId(user.getId());
			loginInfoForm.setNickname(user.getNickname());
			loginInfoForm.setSectionsList(dbConnect.getAllSections());
			loginInfoForm.setLogged(true);
		} else {
			loginInfoForm.setLogged(false);
		}
		return loginInfoForm;
	}
	/*                         ARTICLE ACTIONS                                      */
	public Article getArticleById(int articleId) throws SQLException {
		Article article = null;
		
		article = dbConnect.getArticleById(articleId);
		
		return article;
	}
	
	public List<ArticleWrap> getAllArticles(int sectionId) throws SQLException {
		List<ArticleWrap> list = new ArrayList<ArticleWrap>();
		
		if (0 == sectionId) {
			list = dbConnect.getAllArticles();
		} else {
			list = dbConnect.getAllArticlesFromSection(sectionId);
		}
		
		return list;
	}
	
	public List<String> validateArticle(Article article) {
		List<String> list = new ArrayList<String>();
		
		if (article.getTitle().length() < 10) {
			list.add("Заголовок не может быть короче 10 символов");
		}
		if (article.getTitle().length() > 200) {
			list.add("Заголовок не может быть длинее 200 символов");
		}
		if (article.getContent().length() < 100) {
			list.add("Текст статьи не может содержать менее 100 символов");
		}
		if (article.getContent().length() > 21844) {
			list.add("Текст статьи не может содержать более 21844 символов");
		}
		
		return list;
	}
	
	public void insertArticle(Article article) throws SQLException {
		dbConnect.insertArticle(article);
	}
	
	public void updateArticle(Article article) throws SQLException {
		dbConnect.updateArticle(article);
	}
	/*                         COMMENTS ACTIONS                                     */
	public Comment getCommentById(int commentId) throws SQLException {
		return dbConnect.getCommentById(commentId);
	}
	
	public boolean validateComment(Comment comment) {
		if (comment.getContent().length() < 20 || comment.getContent().length() > 21844) {
			return false;
		}
		return true;
	}
	
	public List<CommentWrap> getCommentsFromArticle(int articleId) throws SQLException {
		return dbConnect.getCommentWrapsFromArticle(articleId);
	}
	
	public void insertComment(Comment comment) throws SQLException {
		dbConnect.insertComment(comment);
	}
	public void updateComment(Comment comment) throws SQLException {
		dbConnect.updateComment(comment);
	}
	/*                         ROLE ACTIONS                                         */
	public List<Role> getRoles(boolean returnAdministrativeRole) throws SQLException {
		if (returnAdministrativeRole) {
			return dbConnect.getAllRoles();
		} else {
			return dbConnect.getNoneAdmistratorRoles();
		}
	}
	/*                         SECTIONS ACTIONS                                     */
	public Section getSectionById(int sectionId) throws SQLException {
		return dbConnect.getSectionById(sectionId);
	}
	/*                         USER ACTIONS                                         */
	public List<User> getAllUsers() throws SQLException {
		return dbConnect.getAllUsers();
	}
	
	public User getUserById(int userId) throws SQLException {
		User user = null;
		
		user = dbConnect.getUserById(userId);
		
		return user;
	}
	public UserProfileForm getUserProfile(int userId) throws SQLException {
		UserProfileForm userProfileForm = null;
		
		User user = dbConnect.getUserById(userId);
		
		if (null != user) {
			userProfileForm = new UserProfileForm();
			
			userProfileForm.setProfileUser(user);
			userProfileForm.setArticleWrapList(dbConnect.getAllArticleWrapFromUser(userId));
			userProfileForm.setCommentWrapList(dbConnect.getCommentWrapFromUser(userId));
			userProfileForm.setProfileUserRoleName(dbConnect.getRoleById(user.getRole()).getName());
		}
		
		return userProfileForm;
	}
	
	public void insertUser(String login, String password, User user) throws SQLException {
		dbConnect.insertUser(login, getMD5Hash(password), user);
	}
	
	
	public void updateUser(User user) throws SQLException {
		dbConnect.updateUser(user);
	}
	
	public void updateUserPassword(String password, int userId) throws SQLException {
		dbConnect.updateUserPassword(getMD5Hash(password), userId);
	}
	
	
	public List<String> validateUserData(String login, String nickname) throws SQLException {
		List<String> list = new ArrayList<String>();
		
		Pattern pattern;
		Matcher matcher;
		
		if (login.length() > 30) {
			list.add("Логин не может быть длиннее 30 символов");
		}
		if (!dbConnect.isLoginUnique(login)) {
			list.add("Данный логин уже занят");
		}
		
		pattern = Pattern.compile("[a-zA-Z]+");
		matcher = pattern.matcher(login);
		
		if (!matcher.matches()) {
			list.add("Логин может содержать только символы английского языка");
		}
		
		if (nickname.length() > 30) {
			list.add("Никнейм не может быть длиннее 30 символов");
		}
		if (!dbConnect.isNicknameUnique(nickname)) {
			list.add("Данный никнейм уже занят");
		}
		
		pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
		matcher = pattern.matcher(nickname);
		
		if (!matcher.matches()) {
			list.add("Никнейм может содержать только символы русского или английского языка");
		}		
		
		return list;
	}
	
	public List<String> validateUserNickname(String nickname) throws SQLException {
		List<String> list = new ArrayList<String>();
		
		Pattern pattern;
		Matcher matcher;
		
		if (nickname.length() > 30) {
			list.add("Никнейм не может быть длиннее 30 символов");
		}
		if (!dbConnect.isNicknameUnique(nickname)) {
			list.add("Данный никнейм уже занят");
		}
		
		pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
		matcher = pattern.matcher(nickname);
		
		if (!matcher.matches()) {
			list.add("Никнейм может содержать только символы русского или английского языка");
		}		
		
		return list;
	}
	
	public boolean isPasswordValid(String password) {
		Pattern pattern;
		Matcher matcher;
		
		pattern = Pattern.compile("[0-9a-zA-Z]+");
		matcher = pattern.matcher(password);
		
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
}
