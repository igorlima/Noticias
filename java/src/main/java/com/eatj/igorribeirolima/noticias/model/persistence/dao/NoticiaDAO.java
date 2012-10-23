package com.eatj.igorribeirolima.noticias.model.persistence.dao;

import java.util.Date;
import java.util.List;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;

public interface NoticiaDAO extends DAO<Noticia, Long> {
	
	List<Noticia> retrieve( Date dataInicio, Date dataFim );
	List<Noticia> retrieve( String sqllucene, Date dataInicio, Date dataFim );
	
}
