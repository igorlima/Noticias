package com.eatj.igorribeirolima.noticias.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.noticias.model.service.bo.LuceneBO;

@Controller
@RequestMapping("/lucene")
public class LuceneController {

	@Inject
	private LuceneBO bo;
	
	@RequestMapping(value = "/indexacao", method = RequestMethod.GET)
	public ReturnTO busca( Date dataInicio, Date dataFim ) {
		return this.bo.indexar();
	}
	
}
