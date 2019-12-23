package deploy.model;

import java.io.Serializable;

public class BodyUser implements Serializable {
	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;
	//private int role;
	//private String name;

	public BodyUser() {

	}

	public BodyUser(String username, String password, int role, String name) {
		this.setUsername(username);
		this.setPassword(password);
		//this.setRole(role);
		//this.setName(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}*/
}
