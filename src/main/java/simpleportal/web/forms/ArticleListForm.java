package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.Article;
import simpleportal.logic.basic_classes.ArticleWrap;

public class ArticleListForm extends InfoForm {
	private List<ArticleWrap> articlesList;

	public List<ArticleWrap> getArticlesList() {
		return articlesList;
	}

	public void setArticlesList(List<ArticleWrap> articlesList) {
		this.articlesList = articlesList;
	}
	
	public boolean isListEmpty() {
		if (0 == articlesList.size()) {
			return true;
		} else {
			return false;
		}
	}
}
