package com.eatj.igorribeirolima.noticias.model.service.bo;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;
import com.eatj.igorribeirolima.noticias.model.persistence.dao.NoticiaDAO;
import com.eatj.igorribeirolima.noticias.util.Util;

@Service
public class NoticiaBO {
	
	private final Log log = LogFactory.getLog( getClass() );

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private NoticiaDAO dao;

	@Transactional( readOnly=true )
	public Noticia busca_por_id ( Long id ){
		return dao.retrieve(id);
	}
	
	@Transactional( readOnly=true )
	public List<Noticia> busca( Date dataInicio, Date dataFim ){
		return dao.retrieve( dataInicio, dataFim );
	}
	
	@Transactional( readOnly=true )
	public List<Noticia> busca( String sqllucene, Date dataInicio, Date dataFim ){
		return dao.retrieve( sqllucene, dataInicio, dataFim );
	}
	
	@Transactional
	public void salva(Noticia noticia){
		dao.createOrUpdate(noticia);
	}
	
	@Transactional
	public void salva( List<Noticia> noticias ) {
		for( Noticia noticia : noticias )
			dao.createOrUpdate(noticia);
	}
	
	@Scheduled(cron="0 0 10 * * SUN-SAT")
	@Transactional
	public void atualizacaoDiariaDasNoticasDaBovespa(){
		atualizacaoDasNoticasDaBovespa( 501L );
	}
	
	@Transactional
	public synchronized ReturnTO atualizacaoDasNoticasDaBovespa( Long indiceDaNoticia ) {
		if( indiceDaNoticia == null || indiceDaNoticia < 25 ) return new MessageReturnTO( ReturnTO.Status.ERROR );
		log.debug( Util.converterDateToString( new Date() ) + ": iniciando atualização das notícias..." );
		
		for( int i=indiceDaNoticia.intValue(); i>0; i-=25){ //6876 ou 7626
			List<Noticia> noticias = NoticiasFolha.getNoticias( "bovespa", new Long(i) );
			verificar_se_existe_as_noticias_no_bd(noticias);
			if( noticias != null && !noticias.isEmpty() )
				salva(noticias);
			log.debug( "Notícias do indice " + i + " atualizada(s). " + noticias.size() + " salva(s)." );
		}
		
		log.debug( Util.converterDateToString( new Date() ) + ": fim da atualização das notícias" );
		return new MessageReturnTO( ReturnTO.Status.SUCCESS, "Notícias atualizadas." );
	}
	
	@Transactional( readOnly=true )
	private void verificar_se_existe_as_noticias_no_bd( List<Noticia> noticias ){
		if( noticias == null || noticias.isEmpty() ) return;
		
		Long dtMin = null;
		Long dtMax = null;
		for( Noticia noticia : noticias ){
			if( dtMin == null ) dtMin = noticia.getData().getTime();
			else dtMin = Math.min( dtMin, noticia.getData().getTime() );
			
			if( dtMax == null ) dtMax = noticia.getData().getTime();
			else dtMax = Math.max( dtMax, noticia.getData().getTime() );
		}
		
		List<Noticia> noticias_gravadas_no_bd = dao.retrieve( new Date(dtMin), new Date(dtMax) );
		if( noticias_gravadas_no_bd.isEmpty() ) return;
		
		for( Noticia noticia : noticias_gravadas_no_bd )
			noticias.remove( noticia );
	}
	
}
