package edu.calpoly.csc.luna.turboplan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.validator.NotEmpty;

import edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtSkill;

@Entity
public class Skill extends BaseEntity implements GwtModelConverter<GwtSkill>, Cloneable {

	@Id
	@GeneratedValue
	private Long skillID;

	@NotEmpty
	private String title;

	@Column(length = Short.MAX_VALUE)
	@Lob
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company", updatable=true)
	private Company company;

	/**
	 * Default constructor
	 * 
	 * @deprecated use other constructors instead. This is only used by
	 *             hibernate to construct reflectively
	 */
	@Deprecated
	public Skill() {
	}

	public Skill(String title, Company company) {
		super();

		this.title = title;
		this.company = company;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Long getSkillID() {
		return skillID;
	}

	public void setSkillID(Long skillID) {
		this.skillID = skillID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public GwtSkill convert2GwtModel() {
		return GwtUtil.hib2gwt(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((skillID == null) ? 0 : skillID.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Skill))
			return false;
		Skill other = (Skill) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (skillID == null) {
			if (other.skillID != null)
				return false;
		} else if (!skillID.equals(other.skillID))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return StringUtil.beanToString(this).toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Skill skill = (Skill) super.clone(); 

		skill.setCompany(company);
		skill.setDescription(description);
		skill.setSkillID(skillID);
		skill.setTitle(title);

		return skill;
	}
	
}
