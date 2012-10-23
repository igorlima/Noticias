package com.eatj.igorribeirolima.noticias.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.ObjectReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;
import com.eatj.igorribeirolima.noticias.model.service.bo.NoticiaBO;

@Controller
@RequestMapping("/noticia/**")
public class NoticiaController {

	@Inject
	private NoticiaBO bo;
	
	@RequestMapping(value = "dataInicio/{dataInicio}/dataFim/{dataFim}", method = RequestMethod.GET)
	public ReturnTO busca( @PathVariable Long dataInicio, @PathVariable Long dataFim ) {
		return new ObjectReturnTO<List<Noticia>>( this.bo.busca( new Date(dataInicio), new Date(dataFim) ) );
	}
	
	@RequestMapping(value = "dataInicio/{dataInicio}/dataFim/{dataFim}", method = RequestMethod.POST)
	public ReturnTO busca( @RequestBody String sqllucene, @PathVariable Long dataInicio, @PathVariable Long dataFim ) {
		return new ObjectReturnTO<List<Noticia>>( this.bo.busca( sqllucene, new Date(dataInicio), new Date(dataFim) ) );
	}
	
	@RequestMapping(value = "/atualizacaoDasNoticias", method = RequestMethod.GET)
	public ReturnTO atualizacaoDasNoticasDaBovespa() {
		return bo.atualizacaoDasNoticasDaBovespa( 1376L );
	}
	
}
