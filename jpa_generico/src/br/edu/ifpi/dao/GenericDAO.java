package br.edu.ifpi.dao;

import java.util.List;
import java.util.Map;

public interface GenericDAO {
	public void save(Object entity);
	public void delete(Object entity);
	public Object find(Class entityClass, Object id);
	public List find(Class entityClass);
	public List find(Class entityClass, int firstResult, int maxResults);
	public List find(String queryName, Map<String, Object> namedParams);
	public List find(String queryName, Map<String, Object> namedParams, int firstResult, int maxResults);
}