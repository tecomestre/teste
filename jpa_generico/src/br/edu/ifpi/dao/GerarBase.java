package br.edu.ifpi.dao;

import java.util.Properties;

import javax.persistence.*;

public class GerarBase {

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.put("toplink.ddl-generation", "drop-and-create-tables");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-toplink-derby", prop);
		EntityManager em = emf.createEntityManager();
		em.close();
		emf.close();
	}

}
