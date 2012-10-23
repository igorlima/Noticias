package com.eatj.igorribeirolima.noticias.util;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory;
	
	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("teste");
	}
	
	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
	
	public void tiraDoCache( Class<?> entityClass, Serializable id ){
		entityManagerFactory.getCache().evict( entityClass, id );
	}
}
