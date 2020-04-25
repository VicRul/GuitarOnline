package models;

public class Users {

	private int userId;
	private String fio;
	private String mail;
	private String phone;
	private String login;
	private String password;
	private boolean isAuthorised;
	
	public Users(int userId, String fio, String mail, String phone, String login, String password, boolean isAuthorised) {
		this.userId = userId;
		this.fio = fio;
		this.mail = mail;
		this.phone = phone;
		this.login = login;
		this.password = password;
		this.isAuthorised = isAuthorised;
	}
	
	public boolean isAuthorised() {
		return isAuthorised;
	}

	public void setAuthorised(boolean isAuthorised) {
		this.isAuthorised = isAuthorised;
	}

	@Override
	public String toString() {
		return "\n\nUsers [Id = " + userId + "\nФИО = " + fio + "\nmail = " + mail + "\nphone = " + phone + "\nlogin = " + login
				+ "\npassword = " + password + "]";
	}
}