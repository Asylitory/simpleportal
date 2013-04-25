package simpleportal.web.forms;

import simpleportal.logic.basic_classes.Comment;

public class CommentEditForm extends InfoForm {
	private Comment comment;
	private boolean error;

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
}
