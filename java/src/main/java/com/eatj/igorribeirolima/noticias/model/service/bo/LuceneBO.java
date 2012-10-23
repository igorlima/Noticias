package com.eatj.igorribeirolima.noticias.model.service.bo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

@Service
public class LuceneBO {
	
	@PersistenceContext( type=PersistenceContextType.TRANSACTION )
	private EntityManager entityManager;
	
	public ReturnTO indexar(){
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
			return new MessageReturnTO( ReturnTO.Status.SUCCESS, "Indexacao realizada com sucesso!" );
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
