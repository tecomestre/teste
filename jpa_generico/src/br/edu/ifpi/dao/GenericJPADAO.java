package br.edu.ifpi.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class GenericJPADAO implements GenericDAO {

	public enum QueryType { JPQL, NATIVE, NAMED }
	
	private EntityManagerFactory emf;
	
	public GenericJPADAO() {
		setEmf(Persistence.createEntityManagerFactory("jpa-hibernate-mysql"));
	}
	
	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#save(java.lang.Object)
	 */
	public void save(Object entity) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(entity);
//			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DAOException("A operação não foi realizada com sucesso.", e);
		} finally {
			em.close();
		}
	}
	
	

	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#delete(java.lang.Object)
	 */
	public void delete(Object entity) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.merge(entity));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new DAOException("A operação não foi realizada com sucesso.", e);
		} finally {
			em.close();
		}
	}

	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#find(java.lang.Class, java.lang.Object)
	 */

	
	public Object find(Class entityClass, Object id) {
		Object result = null;
		EntityManager em = emf.createEntityManager();
		try {
			result = em.find(entityClass, id);
		} finally {
			em.close();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#find(java.lang.Class)
	 */
	public List find(Class entityClass) {
		return find(entityClass, -1, -1);
	}
	
	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#find(java.lang.Class, int, int)
	 */
	public List find(Class entityClass, int firstResult, int maxResults) {
		List result = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query q = em.createQuery("select obj from " + entityClass.getSimpleName() + " obj");
			if (firstResult >= 0 && maxResults >= 0) {
				q = q.setFirstResult(firstResult).setMaxResults(maxResults);
			}
		    result = q.getResultList();   
		} finally {
			em.close();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#find(java.lang.String, java.util.Map)
	 */
	public List find(String queryName, Map<String,Object> namedParams) {
		return find(QueryType.NAMED, queryName, namedParams);
	}
	
	public List find(QueryType type, String query, Map<String,Object> namedParams) {
		return find(type, query, namedParams, -1, -1);
	}
	
	/* (non-Javadoc)
	 * @see br.cefetpi.dao.GenericDAO#find(java.lang.String, java.util.Map, int, int)
	 */
	public List find(String queryName, Map<String,Object> namedParams, int firstResult, int maxResults) {
		return find(QueryType.NAMED, queryName, namedParams, firstResult, maxResults);
	}
	
	public List find(QueryType type, String query, Map<String,Object> namedParams, int firstResult, int maxResults) {
		List result = null;
		EntityManager em = emf.createEntityManager();
		try {
			Query q;
			if (type == QueryType.JPQL) {
				q = em.createQuery(query);
			} else if (type == QueryType.NATIVE) {
				q = em.createNativeQuery(query);
			} else if (type == QueryType.NAMED) {
				q = em.createNamedQuery(query); 
			} else {
				throw new IllegalArgumentException("Tipo de Query inválido: " + type);
			}
			
			// Define os parâmetros da consulta
			Set<String> keys = namedParams.keySet();
			for (String key : keys) {
				q.setParameter(key, namedParams.get(key));
			}
			
			// Define paginação ou não
			if (firstResult >= 0 && maxResults >= 0) {
				q = q.setFirstResult(firstResult).setMaxResults(maxResults);
			}

			// Executa a consulta
		    result = q.getResultList();   
		} finally {
			em.close();
		}
		return result;
	}

}
