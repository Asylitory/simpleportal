package simpleportal.logic.basic_classes;

public class ArticleWrap extends Article {
	private String authorNickname;
	private String editorNickname;
	private String sectionName;
	
	public ArticleWrap() {
		super();
	}
	
	public ArticleWrap(Article article) {
		super();
		this.setId(article.getId());
		this.setTitle(article.getTitle());
		this.setContent(article.getContent());
		this.setAuthorId(article.getAuthorId());
		this.setCreateDate(article.getCreateDate());
		this.setEditDate(article.getEditDate());
		this.setEditorId(article.getEditorId());
		this.setSection(article.getSection());
	}
	
	public String getAuthorNickname() {
		return authorNickname;
	}
	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}
	public String getEditorNickname() {
		return editorNickname;
	}
	public void setEditorNickname(String editorNickname) {
		this.editorNickname = editorNickname;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
}
