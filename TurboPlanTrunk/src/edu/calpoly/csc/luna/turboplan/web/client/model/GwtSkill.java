package edu.calpoly.csc.luna.turboplan.web.client.model;



public class GwtSkill implements GwtModel {
	private Long skillID;
	private String title;
	private String description;
	private GwtCompany company;
	
	public GwtSkill() {}

	public GwtSkill(String title, GwtCompany company) {
		super();

		this.title = title;
		this.company = company;
	}

	public Long getSkillID() {
		return skillID;
	}

	public void setSkillID(Long skillID) {
		this.skillID = skillID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCompany(GwtCompany company) {
		this.company = company;
	}

	public GwtCompany getCompany() {
		return company;
	}

}
