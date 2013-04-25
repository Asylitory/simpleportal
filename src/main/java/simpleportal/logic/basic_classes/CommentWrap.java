package simpleportal.logic.basic_classes;

public class CommentWrap extends Comment {
	private String authorNickname;
	private String editorNickname;
	
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
}
