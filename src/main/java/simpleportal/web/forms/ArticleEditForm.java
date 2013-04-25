package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.Article;

public class ArticleEditForm extends InfoForm {
	private Article article;
	private List<String> errors = null;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
