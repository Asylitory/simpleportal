package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.ArticleWrap;
import simpleportal.logic.basic_classes.Comment;
import simpleportal.logic.basic_classes.CommentWrap;

public class ArticleForm extends InfoForm {
	private ArticleWrap articleWrap;
	private List<CommentWrap> commentWrapList;
	
	public boolean isCommentsEmpty() {
		if (0 == commentWrapList.size()) {
			return true;
		}
		return false;
	}

	public ArticleWrap getArticleWrap() {
		return articleWrap;
	}

	public void setArticleWrap(ArticleWrap articleWrap) {
		this.articleWrap = articleWrap;
	}

	public List<CommentWrap> getCommentWrapList() {
		return commentWrapList;
	}

	public void setCommentWrapList(List<CommentWrap> commentWrapList) {
		this.commentWrapList = commentWrapList;
	}
}