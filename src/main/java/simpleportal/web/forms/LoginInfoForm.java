package simpleportal.web.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;

public class LoginInfoForm extends InfoForm {
	private List<Cookie> cookies = new ArrayList<Cookie>();

	public List<Cookie> getCookies() {
		return cookies;
	}
	
	public void addCookie(Cookie cookie) {
		cookies.add(cookie);
	}
}
