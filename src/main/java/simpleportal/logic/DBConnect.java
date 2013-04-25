package simpleportal.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import simpleportal.logic.basic_classes.Article;
import simpleportal.logic.basic_classes.ArticleWrap;
import simpleportal.logic.basic_classes.Comment;
import simpleportal.logic.basic_classes.CommentWrap;
import simpleportal.logic.basic_classes.Role;
import simpleportal.logic.basic_classes.Section;
import simpleportal.logic.basic_classes.User;

public class DBConnect {
	private static DBConnect instance;
	private static Connection connection;
	private static DataSource dataSource;
	
	private DBConnect() {}
	
	public static synchronized DBConnect getInstance() {
		if (null == instance) {
			try {
				instance = new DBConnect();
				Context context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/simpleportalDS");
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/*                   ARTICLES QUERIES                               */
	
	public Article getArticleById(int articleId) throws SQLException {
		Article article = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT id, title, content, author_id, " +
					"create_date, editor_id, edit_date, section " +
					"FROM articles " +
					"WHERE id=?");
			stmt.setInt(1, articleId);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				article = new Article();
				
				article.setId(rs.getInt(1));
				article.setTitle(rs.getString(2));
				article.setContent(rs.getString(3));
				article.setAuthorId(rs.getInt(4));
				article.setCreateDate(rs.getDate(5));
				article.setEditorId(rs.getInt(6));
				article.setEditDate(rs.getDate(7));
				article.setSection(rs.getInt(8));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return article;
	}
	
	public List<ArticleWrap> getAllArticles() throws SQLException {
		List<ArticleWrap> list = new ArrayList<ArticleWrap>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			
			rs = stmt.executeQuery(
					"SELECT a.id, a.title, a.author_id, u.nickname, a.create_date, s.name " +
					"FROM articles a " +
					"INNER JOIN users u " +
					"ON a.author_id = u.id " +
					"INNER JOIN sections s " +
					"ON s.id = a.section " +
					"ORDER BY a.create_date");
			while (rs.next()) {
				ArticleWrap article = new ArticleWrap();
				
				article.setId(rs.getInt(1));
				article.setTitle(rs.getString(2));
				article.setAuthorId(rs.getInt(3));
				article.setAuthorNickname(rs.getString(4));
				article.setCreateDate(rs.getDate(5));
				article.setSectionName(rs.getString(6));
				
				list.add(article);
			}			
			
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public List<ArticleWrap> getAllArticlesFromSection(int sectionId) throws SQLException {
		List<ArticleWrap> list = new ArrayList<ArticleWrap>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(				
					"SELECT a.id, a.title, a.author_id, u.nickname, a.create_date, s.name " +
					"FROM articles a " +
					"INNER JOIN users u " +
					"ON a.author_id = u.id " +
					"INNER JOIN sections s " +
					"ON s.id = a.section " +
					"WHERE a.section=? " +
					"ORDER BY a.create_date");
			stmt.setInt(1, sectionId);
			rs = stmt.executeQuery();			
			
			while (rs.next()) {
				ArticleWrap article = new ArticleWrap();
				
				article.setId(rs.getInt(1));
				article.setTitle(rs.getString(2));
				article.setAuthorId(rs.getInt(3));
				article.setAuthorNickname(rs.getString(4));
				article.setCreateDate(rs.getDate(5));
				article.setSectionName(rs.getString(6));
				
				list.add(article);
			}	
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public List<ArticleWrap> getAllArticleWrapFromUser(int userId) throws SQLException {
		List<ArticleWrap> list = new ArrayList<ArticleWrap>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT a.id, a.title, a.author_id, u.nickname, a.create_date, s.name " +
					"FROM articles a " +
					"INNER JOIN users u " +
					"ON a.author_id = u.id " +
					"INNER JOIN sections s " +
					"ON s.id = a.section " +
					"WHERE a.author_id=? " +
					"ORDER BY a.create_date");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();			

			while (rs.next()) {
				ArticleWrap articleWrap = new ArticleWrap();
				
				articleWrap.setId(rs.getInt(1));
				articleWrap.setTitle(rs.getString(2));
				articleWrap.setAuthorId(rs.getInt(3));
				articleWrap.setAuthorNickname(rs.getString(4));
				articleWrap.setCreateDate(rs.getDate(5));
				articleWrap.setSectionName(rs.getString(6));
				
				list.add(articleWrap);							
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public void insertArticle(Article article) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"INSERT INTO articles " +
					"(title, content, author_id, create_date, " +
					"editor_id, edit_date, section) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, article.getTitle());
			stmt.setString(2, article.getContent());
			stmt.setInt(3, article.getAuthorId());
			stmt.setDate(4, new java.sql.Date(article.getCreateDate().getTime()));
			stmt.setInt(5, article.getEditorId());
			stmt.setDate(6, new java.sql.Date(article.getEditDate().getTime()));
			stmt.setInt(7, article.getSection());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	public void updateArticle(Article article) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"UPDATE articles " +
					"SET title=?, content=?, editor_id=?, edit_date=?, section=? " +
					"WHERE id=?");
			stmt.setString(1, article.getTitle());
			stmt.setString(2, article.getContent());
			stmt.setInt(3, article.getEditorId());
			stmt.setDate(4, new java.sql.Date(article.getEditDate().getTime()));
			stmt.setInt(5, article.getSection());
			stmt.setInt(6, article.getId());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	/*                              COMMENTS QUERIES                                   */
	
	public Comment getCommentById(int commentId) throws SQLException {
		Comment comment = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT id, content, author_id, create_date, editor_id, edit_date, article_id " +
					"FROM comments " +
					"WHERE id=?");
			stmt.setInt(1, commentId);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				comment = new Comment();
				
				comment.setId(rs.getInt(1));
				comment.setContent(rs.getString(2));
				comment.setAuthorId(rs.getInt(3));
				comment.setCreateDate(rs.getDate(4));
				comment.setEditorId(rs.getInt(5));
				comment.setEditDate(rs.getDate(6));
				comment.setArticleId(rs.getInt(7));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return comment;
	}
	
	public List<CommentWrap> getCommentWrapsFromArticle(int articleId) throws SQLException {
		List<CommentWrap> list = new ArrayList<CommentWrap>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT c.id, c.content, c.author_id, ua.nickname, c.create_date, c.editor_id, ue.nickname, c.edit_date " +
					"FROM comments c " +
					"INNER JOIN users ua " +
					"ON c.author_id = ua.id " +
					"INNER JOIN users ue " +
					"ON c.editor_id = ue.id " +
					"WHERE c.article_id=? " +
					"ORDER BY c.create_date");
			stmt.setInt(1, articleId);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommentWrap commentWrap = new CommentWrap();
				
				commentWrap.setId(rs.getInt(1));
				commentWrap.setContent(rs.getString(2));
				commentWrap.setAuthorId(rs.getInt(3));
				commentWrap.setAuthorNickname(rs.getString(4));
				commentWrap.setCreateDate(rs.getDate(5));
				commentWrap.setEditorId(rs.getInt(6));
				commentWrap.setEditorNickname(rs.getString(7));
				commentWrap.setEditDate(rs.getDate(8));
				
				list.add(commentWrap);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public List<CommentWrap> getCommentWrapFromUser(int userId) throws SQLException {
		List<CommentWrap> list = new ArrayList<CommentWrap>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT c.id, c.content, c.author_id, ua.nickname, c.create_date, c.editor_id, ue.nickname, c.edit_date " +
					"FROM comments c " +
					"INNER JOIN users ua " +
					"ON c.author_id = ua.id " +
					"INNER JOIN users ue " +
					"ON c.editor_id = ue.id " +
					"WHERE c.author_id=? " +
					"ORDER BY c.create_date");
			stmt.setInt(1, userId);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommentWrap commentWrap = new CommentWrap();
				
				commentWrap.setId(rs.getInt(1));
				commentWrap.setContent(rs.getString(2));
				commentWrap.setAuthorId(rs.getInt(3));
				commentWrap.setAuthorNickname(rs.getString(4));
				commentWrap.setCreateDate(rs.getDate(5));
				commentWrap.setEditorId(rs.getInt(6));
				commentWrap.setEditorNickname(rs.getString(7));
				commentWrap.setEditDate(rs.getDate(8));
					
				list.add(commentWrap);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public void insertComment(Comment comment) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"INSERT INTO comments " +
					"(content, author_id, create_date, editor_id, " +
					"edit_date, article_id)" +
					"VALUES (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, comment.getContent());
			stmt.setInt(2, comment.getAuthorId());
			stmt.setDate(3, new java.sql.Date(comment.getCreateDate().getTime()));
			stmt.setInt(4, comment.getEditorId());
			stmt.setDate(5, new java.sql.Date(comment.getEditDate().getTime()));
			stmt.setInt(6, comment.getArticleId());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	public void updateComment(Comment comment) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(
					"UPDATE comments " +
					"SET content=?, editor_id=?, edit_date=? " +
					"WHERE id=?");
			stmt.setString(1, comment.getContent());
			stmt.setInt(2, comment.getEditorId());
			stmt.setDate(3, new java.sql.Date(comment.getEditDate().getTime()));
			stmt.setInt(4, comment.getId());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	/*                    ROLES QUERIES                                        */
	
	public Role getRoleById(int roleId) throws SQLException {
		Role role = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT id, name " +
					"FROM roles " +
					"WHERE id=?");
			stmt.setInt(1, roleId);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				role = new Role();
				
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return role;
	}
	
	public List<Role> getNoneAdmistratorRoles() throws SQLException {
		List<Role> list = new ArrayList<Role>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(
					"SELECT id, name " +
					"FROM roles " +
					"WHERE id != ?");
			stmt.setInt(1, 1);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
				
				list.add(role);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public List<Role> getAllRoles() throws SQLException {
		List<Role> list = new ArrayList<Role>();
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT id, name " +
					"FROM roles");
			
			while (rs.next()) {
				Role role = new Role();
				
				role.setId(rs.getInt(1));
				role.setName(rs.getString(2));
				
				list.add(role);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	/*               SECTIONS QUERIES                          */
	public Section getSectionById(int sectionId) throws SQLException {
		Section section = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT id, name, description " +
					"FROM sections " +
					"WHERE id=?");
			stmt.setInt(1, sectionId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				section = new Section();
				
				section.setId(rs.getInt(1));
				section.setName(rs.getString(2));
				section.setDescription(rs.getString(3));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return section;
	}
	
	public List<Section> getAllSections() throws SQLException {
		List<Section> list = new ArrayList<Section>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT id, name, description " +
					"FROM sections");
			
			while (rs.next()) {
				Section section = new Section();
				
				section.setId(rs.getInt(1));
				section.setName(rs.getString(2));
				section.setDescription(rs.getString(3));
				
				list.add(section);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	/*                            USERS QUERIES                   */
	
	public User getUserById(int userId) throws SQLException {
		User user = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT id, nickname, join_date, role " +
					"FROM users " +
					"WHERE id = ?");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				user = new User();
				
				user.setId(rs.getInt(1));
				user.setNickname(rs.getString(2));
				user.setJoinDate(rs.getDate(3));
				user.setRole(rs.getInt(4));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return user;
	}
	
	public User getUserByLogin(String login) throws SQLException {
		User user = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT id, nickname, join_date, role " +
					"FROM users " +
					"WHERE login = ?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();
			if (rs.next()) {
				user = new User();
				
				user.setId(rs.getInt(1));
				user.setNickname(rs.getString(2));
				user.setJoinDate(rs.getDate(3));
				user.setRole(rs.getInt(4));
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return user;
	}
	
	public List<User> getAllUsers() throws SQLException {
		List<User> list = new ArrayList<User>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(
					"SELECT id, nickname, join_date, role " +
					"FROM users " +
					"ORDER BY nickname");
			while (rs.next()) {
				User user = new User();
				
				user.setId(rs.getInt(1));
				user.setNickname(rs.getString(2));
				user.setJoinDate(rs.getDate(3));
				user.setRole(rs.getInt(4));
				
				list.add(user);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return list;
	}
	
	public void insertUser(String login, String passwordHash, User user) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(
					"INSERT INTO users " +
					"(login, password, nickname, join_date, role) " +
					"VALUES (?, ?, ?, ?, ?)");
			stmt.setString(1, login);
			stmt.setString(2, passwordHash);
			stmt.setString(3, user.getNickname());
			stmt.setDate(4, new java.sql.Date(user.getJoinDate().getTime()));
			stmt.setInt(5, user.getRole());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	public void updateUser(User user) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(
					"UPDATE users " +
					"SET nickname=?, role=? " +
					"WHERE id=?");
			stmt.setString(1, user.getNickname());
			stmt.setInt(2, user.getRole());
			stmt.setInt(3, user.getId());
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	public void updateUserPassword(String passwordHash, int userId) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(
					"UPDATE users " +
					"SET password=? " +
					"WHERE id=?");
			stmt.setString(1, passwordHash);
			stmt.setInt(2, userId);
			stmt.execute();
		} finally {
			if (null != stmt) {
				stmt.close();
			}
		}
	}
	
	public String getPasswordHashByLogin(String login) throws SQLException {
		String passwordHash = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT password " +
					"FROM users " +
					"WHERE login = ?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();
			if (rs.next()) {
				passwordHash = rs.getString(1);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return passwordHash;
	}
	
	public String getPasswordHashById(int userId) throws SQLException {
		String passwordHash = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT password " +
					"FROM users " +
					"WHERE id = ?");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				passwordHash = rs.getString(1);
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return passwordHash;
	}
	
	public boolean isLoginUnique(String login) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT login " +
					"FROM users " +
					"WHERE login=?");
			stmt.setString(1, login);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return true;
	}
	
	public boolean isNicknameUnique(String nickname) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(
					"SELECT nickname " +
					"FROM users " +
					"WHERE nickname=?");
			stmt.setString(1, nickname);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}
		} finally {
			if (null != rs) {
				rs.close();
			}
			if (null != stmt) {
				stmt.close();
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
