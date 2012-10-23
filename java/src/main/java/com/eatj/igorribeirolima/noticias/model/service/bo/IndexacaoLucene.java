package com.eatj.igorribeirolima.noticias.model.service.bo;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;
import com.eatj.igorribeirolima.noticias.util.JPAUtil;

public class IndexacaoLucene {
	public static void main(String[] args) {
		EntityManager em = new JPAUtil().getEntityManager();
		em.createQuery( "select noticia from Noticia noticia" ).getResultList();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		try {
			fullTextEntityManager.createIndexer( Noticia.class ).startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
