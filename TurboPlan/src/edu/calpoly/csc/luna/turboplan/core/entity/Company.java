package edu.calpoly.csc.luna.turboplan.core.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.NotEmpty;

import edu.calpoly.csc.luna.turboplan.axis2ws.mobile.model.MobileCompany;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.GwtModelConverter;
import edu.calpoly.csc.luna.turboplan.core.modelconverter.MobileModelConverter;
import edu.calpoly.csc.luna.turboplan.core.util.GwtUtil;
import edu.calpoly.csc.luna.turboplan.core.util.MobileUtil;
import edu.calpoly.csc.luna.turboplan.core.util.StringUtil;
import edu.calpoly.csc.luna.turboplan.web.client.model.GwtCompany;

@Entity
public class Company extends BaseEntity implements GwtModelConverter<GwtCompany>, MobileModelConverter<MobileCompany> {

	@Id
	@GeneratedValue
	private Long companyID;

	@NotEmpty
	private String name;

	@NotEmpty
	private String address;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "company")
	@BatchSize(size = 10)
	private Set<AbstractUser> users;
	
	@OneToMany (fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "company")
	@BatchSize(size = 10)
	private Set<Skill> skills;

	@OneToMany (fetch=FetchType.LAZY)
	@JoinColumn (name="company")
	@BatchSize(size = 10)
	private Set<Task> tasks;
	
	/**
	 * Default constructor
	 * 
	 * @deprecated use other constructors instead. This is only used by
	 *             hibernate to construct reflectively
	 */
	@Deprecated
	public Company() {}

	public Company(String name, String address) {
		super();

		this.name = name;
		this.address = address;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public Set<AbstractUser> getUsers() {
		return users;
	}

	public void setUsers(Set<AbstractUser> users) {
		this.users = users;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public GwtCompany convert2GwtModel() {
		return GwtUtil.hib2gwt(this);
	}

	@Override
	public MobileCompany convert2MobileModel() {
		return MobileUtil.hib2mobile(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Company))
			return false;
		Company other = (Company) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return StringUtil.beanToString(this, "users", "skills", "tasks").toString();
	}
	
}
