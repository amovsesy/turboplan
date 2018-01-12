package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.List;

import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.Skill;

public class SkillDao extends BaseDao<Skill> {
	private static SkillDao singleton = new SkillDao();
	
	public static SkillDao getInstance() {
		return singleton;
	}
	
	public void addSkill(Skill nSkill) {
		super.save(nSkill);
	}
	
	public void updateSkill(Skill skill) {
		super.update(skill);
	}
	
	public void deleteSkill(Skill skill) {
		super.delete(skill);
	}
	
	public List<Skill> getAllSkill() {
		return getAll(Skill.class);
	}
	
	public List<Skill> getSkillsBelongToCompanyById(Long id) {
		Session session = newSessionAndTransaction();
		
		StringBuilder query = new StringBuilder("from ").append(EntityConstants.Skill);
		query.append(" where company = ?");
		
		@SuppressWarnings("unchecked")
		List<Skill> ret = session.createQuery(query.toString()).setLong(0, id).list();
		
		session.getTransaction().commit();
		
		return ret;
	}

	@Override
	public int getTableRowCount() {
		return super.countRowOfTable(Skill.class);
	}

	@Override
	public String printTable() {
		return super.printTable(Skill.class).toString();
	}

	
}
