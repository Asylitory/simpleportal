package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.ArticleWrap;
import simpleportal.logic.basic_classes.CommentWrap;
import simpleportal.logic.basic_classes.User;

public class UserProfileForm extends InfoForm {
	private User profileUser;
	private List<ArticleWrap> articleWrapList;
	private List<CommentWrap> commentWrapList;
	private String profileUserRoleName;
	
	public boolean isArticleWrapListEmpty() {
		if (0 == articleWrapList.size()) {
			return true;
		}
		return false;
	}
	
	public boolean isCommentWrapListEmpty() {
		if (0 == commentWrapList.size()) {
			return true;
		}
		return false;
	}
	
	public User getProfileUser() {
		return profileUser;
	}
	public void setProfileUser(User profileUser) {
		this.profileUser = profileUser;
	}
	public List<ArticleWrap> getArticleWrapList() {
		return articleWrapList;
	}
	public void setArticleWrapList(List<ArticleWrap> articleWrapList) {
		this.articleWrapList = articleWrapList;
	}
	public List<CommentWrap> getCommentWrapList() {
		return commentWrapList;
	}
	public void setCommentWrapList(List<CommentWrap> commentWrapList) {
		this.commentWrapList = commentWrapList;
	}
	public String getProfileUserRoleName() {
		return profileUserRoleName;
	}
	public void setProfileUserRoleName(String profileUserRoleName) {
		this.profileUserRoleName = profileUserRoleName;
	}
}
