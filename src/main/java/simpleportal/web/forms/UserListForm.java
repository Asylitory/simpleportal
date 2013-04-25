package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.Role;
import simpleportal.logic.basic_classes.User;

public class UserListForm extends InfoForm {
	private List<User> usersList;
	private List<Role> rolesList;
	
	public List<User> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	public List<Role> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}
}
