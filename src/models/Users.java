package models;

public class Users {

	private int userId;
	private String fio;
	private String mail;
	private String phone;
	private String login;
	private String password;
	
	public Users(int userId, String fio, String mail, String phone, String login, String password) {
		this.userId = userId;
		this.fio = fio;
		this.mail = mail;
		this.phone = phone;
		this.login = login;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", fio=" + fio + ", mail=" + mail + ", phone=" + phone + ", login=" + login
				+ ", password=" + password + "]";
	}
}