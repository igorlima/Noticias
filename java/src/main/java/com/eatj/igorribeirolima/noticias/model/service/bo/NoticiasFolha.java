package com.eatj.igorribeirolima.noticias.model.service.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.client.RestTemplate;

import com.eatj.igorribeirolima.noticias.model.domain.entity.Noticia;
import com.eatj.igorribeirolima.noticias.model.domain.type.Regex;

public class NoticiasFolha {
	
	private static final RestTemplate restTemplate = new RestTemplate();
	
	public static List<Noticia> getNoticias( String palavraChave, Long indice ){
		
		List<Noticia> noticias = new ArrayList<Noticia>();
		String pesquisa = pesquisarNoticiaFromFOLHA(palavraChave, indice);
		
		String INICIO_LINK = ".</b> <a href=\"";
		String FIM_LINK = "\">";
		String FIM_MANCHETE = "</a><br>";
		String FIM_CONTEUDO = "<br>";
		
		while( pesquisa.indexOf( INICIO_LINK ) != -1 ){
			try{
				Noticia noticia = new Noticia();
				
				//pegar link
				pesquisa = pesquisa.substring( pesquisa.indexOf( INICIO_LINK ) + INICIO_LINK.length() );
				noticia.setLink( pesquisa.substring( 0, pesquisa.indexOf( FIM_LINK ) ) );
				
				//pegar manchete
				pesquisa = pesquisa.substring( pesquisa.indexOf( FIM_LINK ) + FIM_LINK.length() );
				noticia.setManchete( tratarString( pesquisa.substring( 0, pesquisa.indexOf( FIM_MANCHETE ) ) ) );
				
				//pegar conteudo
				pesquisa = pesquisa.substring( pesquisa.indexOf( FIM_MANCHETE ) + FIM_MANCHETE.length() );
				noticia.setConteudo( tratarString( pesquisa.substring( 0, pesquisa.indexOf( FIM_CONTEUDO ) ) ) );
				
				noticia.setData( getDate( noticia.getManchete() ) );
				
				//adicionar noticia à lista
				if( noticia.getManchete() != null && noticia.getConteudo() != null && noticia.getData() != null )
					if( noticia.getManchete().toLowerCase().contains( palavraChave.toLowerCase() ) )
						noticias.add(noticia);
				
			}catch( StringIndexOutOfBoundsException stringIndexOutOfBoundsException ){
				System.out.println( stringIndexOutOfBoundsException.getMessage() );
			}
		}
		
		return noticias;
	}
	
	private static String pesquisarNoticiaFromFOLHA( String palavraChave, Long indice ){
		
		try {
			Map<String, String > map = new HashMap<String, String >();
			map.put( "q", palavraChave );
			map.put( "sr", "" + indice );
			
			String str = restTemplate.getForObject( "http://search.folha.com.br/search?q={q}&sr={sr}", String.class, map );
			return str;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return "";
		}
		
	}
	
	private static String tratarString( String str ){
		
		str = str.replaceAll( "[<][b][>]" , "" );
		str = str.replaceAll( "[<][/][b][>]" , "" );
		str = str.replaceAll( "\\r" , "" );
		str = str.replaceAll( "\\n" , "" );
		
		return str.trim();
	}
	
	private static Date getDate( String str ) {
		final String regexData = Regex.ONLY_DATE.get(); // "[0-9][0-9][/][0-9][0-9][/][0-9][0-9][0-9][0-9]";
		
		Pattern pattern = Pattern.compile(regexData);
		Matcher matcher = pattern.matcher(str);
		
		if( matcher.find() ){
			String data = matcher.group();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			try {
				return format.parse(data);
			}catch( ParseException e ){
				return null;
			}
			
		}else{
			return null;
		}
		
	}
	
}
