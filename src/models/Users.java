package models;

public class Users {

	private int userId;
	private String fio;
	private String mail;
	private String phone;
	private String password;
	
	public Users(int userId, String fio, String mail, String phone, String login, String password) {
		this.userId = userId;
		this.fio = fio;
		this.mail = mail;
		this.phone = phone;
		this.password = password;
	}

	
	public int getUserId() {
		return userId;
	}

	public String getFio() {
		return fio;
	}

	public String getMail() {
		return mail;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "\n\nUsers [Id = " + userId + "\nФИО = " + fio + "\nmail = " + mail + "\nphone = " + phone + "\npassword = " + password + "]";
	}
}