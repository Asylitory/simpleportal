package simpleportal.web.forms;

import java.util.List;

import simpleportal.logic.basic_classes.Section;

public class InfoForm {
	private boolean logged;
	private String nickname;
	private String title;
	private int userRole;
	private int userId;
	private int action;
	private String actionMessage;
	
	private List<Section> sectionsList;

	public void setInfo(InfoForm infoForm) {
		this.logged = infoForm.logged;
		this.nickname = infoForm.nickname;
		this.userId = infoForm.userId;
		this.userRole = infoForm.userRole;
		this.sectionsList = infoForm.sectionsList;
	}
	
	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getActionMessage() {
		return actionMessage;
	}

	public void setActionMessage(String actionMessage) {
		this.actionMessage = actionMessage;
	}

	public List<Section> getSectionsList() {
		return sectionsList;
	}

	public void setSectionsList(List<Section> sectionsList) {
		this.sectionsList = sectionsList;
	}
}
