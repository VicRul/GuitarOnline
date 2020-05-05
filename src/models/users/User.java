package models.users;

public class User {

	private int userId;
	private String fio;
	private String mail;
	private String phone;
	
	public User(int userId, String fio, String mail, String phone) {
		this.userId = userId;
		this.fio = fio;
		this.mail = mail;
		this.phone = phone;
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

	@Override
	public String toString() {
		return "\n\nUsers [Id = " + userId + "\nФИО = " + fio + "\nmail = " + mail + "\nphone = " + phone + "]";
	}
}