package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.Role;
import simpleportal.logic.basic_classes.User;

public class UserEditForm extends InfoForm {
	private User editedUser;
	private List<String> errors = null;
	private List<Role> roles = null;
	
	public User getEditedUser() {
		return editedUser;
	}
	public void setEditedUser(User editedUser) {
		this.editedUser = editedUser;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
