package com.eatj.igorribeirolima.noticias.model.persistence.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;
import com.eatj.igorribeirolima.noticias.model.persistence.dao.NoticiaDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class NoticiaHibernateDAO extends HibernateDAO<Noticia, Long> implements NoticiaDAO {
	
	@SuppressWarnings("unchecked")
	public List<Noticia> retrieve( Date dataInicio, Date dataFim ){
		String jpql = "select noticia from Noticia noticia where 1=1 ";
		
		if(dataInicio!=null) jpql += " AND noticia.data >= :dataInicio ";
		if(dataFim!=null) jpql += " AND noticia.data <= :dataFim ";
		jpql += " order by noticia.data desc";
		
		Query query = this.entityManager.createQuery( jpql, Noticia.class );
		
		if(dataInicio!=null) query.setParameter( "dataInicio", dataInicio );
		if(dataFim!=null) query.setParameter( "dataFim", dataFim );
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Noticia> retrieve( String sqllucene, Date dataInicio, Date dataFim ){
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager( this.entityManager );
		QueryParser parser = new QueryParser( Version.LUCENE_33, "manchete", new BrazilianAnalyzer( Version.LUCENE_33 ));
		try{
			Criteria criteria = this.getSession().createCriteria( Noticia.class ).
				add( Restrictions.le( "data", dataFim) ).
				add( Restrictions.ge( "data", dataInicio));
			
			org.apache.lucene.search.Query query = parser.parse( sqllucene );
			FullTextQuery textQuery = fullTextEntityManager.createFullTextQuery(query, Noticia.class).setCriteriaQuery(criteria);
			
			return (List<Noticia>) textQuery.getResultList();
		}catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
