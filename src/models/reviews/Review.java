package models.reviews;

public class Review {
	
	private String title;
	private String fio;
	private String advantages;
	private String disadvantages;
	private String comment;
	private String dateReview;
	
	public Review(String title, String fio, String advantages, String disadvantages, String comment,
			String dateReview) {
		
		this.title = title;
		this.fio = fio;
		this.advantages = advantages;
		this.disadvantages = disadvantages;
		this.comment = comment;
		this.dateReview = dateReview;
	}

	
	public String getTitle() {
		return title;
	}

	public String getFio() {
		return fio;
	}

	public String getAdvantages() {
		return advantages;
	}

	public String getDisadvantages() {
		return disadvantages;
	}

	public String getComment() {
		return comment;
	}

	public String getDateReview() {
		return dateReview;
	}


	@Override
	public String toString() {
		return "\nТовар: " + title + "\nПользователь: " + fio + "\nПлюсы: " + advantages + "\nМинусы: "
				+ disadvantages + "\nКомментарии: " + comment + "\nДата: " + dateReview + "\n";
	}	
}