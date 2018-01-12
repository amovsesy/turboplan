package edu.calpoly.csc.luna.turboplan.core.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.calpoly.csc.luna.turboplan.core.entity.BaseEntity;
import edu.calpoly.csc.luna.turboplan.core.util.HibernateUtil;

public abstract class BaseDao<T extends BaseEntity> {
	private static final Logger log = Logger.getLogger(BaseDao.class);

	/**
	 * Gets the current session
	 * 
	 * @deprecated use newSessionAndTransaction() instead
	 * @return the current session
	 */
	@Deprecated
	protected Session newSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	/**
	 * Get the current session and start a new transaction.
	 * 
	 * @return
	 */
	protected Session newSessionAndTransaction() {
		@SuppressWarnings("deprecation")
		Session session = newSession();

		session.beginTransaction().begin();

		return session;
	}
	
	/**
	 * saveOfUpdate the database entity
	 * 
	 * @param obj
	 */
	protected void saveOrUpdate(T obj) {
		Session session = newSessionAndTransaction();

		session.saveOrUpdate(obj);
		session.getTransaction().commit();
	}


	public <C extends Collection<T>> void saveOrUpdate(C collection, int batchSize) {
		// TODO Auto-generated method stub
		Session session = newSessionAndTransaction();
		
		int count = 0;
		for (T obj : collection) {
			session.saveOrUpdate(obj);
			count++;
			
			if (count % batchSize == 0) {
				session.flush();
				session.clear();
			}
		}
		
		session.getTransaction().commit();
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.) This operation cascades to associated
	 * instances if the association is mapped with cascade="save-update".
	 * 
	 * @param obj
	 */
	protected void save(T obj) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Adding ").append(obj.getClass().getSimpleName());
		logBuilder.append(": ").append(obj);
		log.trace(logBuilder);
		
		Session session = newSessionAndTransaction();
		session.save(obj);
		session.getTransaction().commit();
	}
	
	protected <C extends Collection<T>> void saveMultiple(C collection, final int batchSize) {
		Session session = newSessionAndTransaction();
		
		int count = 0;
		for (T obj : collection) {
			session.save(obj);
			count++;
			
			if (count % batchSize == 0) {
				session.flush();
				session.clear();
			}
		}
		
		session.getTransaction().commit();
	}

	/**
	 * Update the persistent instance with the identifier of the given detached
	 * instance. If there is a persistent instance with the same identifier, an
	 * exception is thrown. This operation cascades to associated instances if
	 * the association is mapped with cascade="save-update".
	 * 
	 * @param obj
	 */
	protected void update(T obj) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Updating ").append(obj.getClass().getSimpleName());
		logBuilder.append(": ").append(obj);
		log.trace(logBuilder);

		Session session = newSessionAndTransaction();
		session.update(obj);
		session.getTransaction().commit();
	}
	
	protected void delete(T obj) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Deleting ").append(obj.getClass().getSimpleName());
		logBuilder.append(": ").append(obj);
		log.trace(logBuilder);

		Session session = newSessionAndTransaction();
		session.delete(obj);
		session.getTransaction().commit();
	}

	/**
	 * Get an entitiy by it's primary key (id)
	 * 
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T getEntityById(Class<T> clazz, Long id) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Getting ").append(clazz.getSimpleName());
		logBuilder.append(" by id: ").append(id);
		log.trace(logBuilder);

		Session session = newSessionAndTransaction();

		T ret = (T) session.get(clazz, id);

		session.getTransaction().commit();

		return ret;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAll(Class<T> clazz) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Getting all ").append(clazz.getSimpleName());
		log.trace(logBuilder);

		Session session = newSessionAndTransaction();

		List<T> list = (List<T>) session.createQuery("from " + clazz.getName()).list();
		
		session.getTransaction().commit();

		return list;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getAll(Class<T> clazz, int fetchSize) {
		StringBuilder logBuilder = new StringBuilder();
		logBuilder.append("Getting all ").append(clazz.getSimpleName());
		logBuilder.append(" with fetchSize: ").append(fetchSize);
		log.trace(logBuilder);

		Session session = newSessionAndTransaction();

		List<T> list = (List<T>) session.createCriteria(clazz).setFetchSize(fetchSize).list();

		session.getTransaction().commit();

		return list;
	}
	
	public abstract int getTableRowCount();

	protected int countRowOfTable(Class<T> clazz) {
		Session session = newSessionAndTransaction();
		
		Integer count = (Integer) session.createSQLQuery("select count(*) from " + clazz.getSimpleName()).uniqueResult();
		
		session.getTransaction().commit();
		
		return count;
	}

	public abstract String printTable();
	
	protected StringBuilder printTable(Class<T> clazz) {
		Session session = newSessionAndTransaction();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>) session.createSQLQuery("select * from " + clazz.getSimpleName()).list();
		
		session.getTransaction().commit();

		StringBuilder builder = new StringBuilder();
		
		for (Object[] row : list) {
			for (Object col : row) {
				builder.append(col.toString());
				builder.append('\t');
			}
			builder.append('\n');
		}
		
		return builder;
	}
}
