package com.eatj.igorribeirolima.noticias.model.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;


@Entity
@Table( name="noticia" )
@Cache( usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
@Indexed
public class Noticia implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private Date data;
	
	@Field( index=Index.TOKENIZED )
	private String manchete;
	private String conteudo;
	private String link;
	
	public Noticia() {
		// TODO Auto-generated constructor stub
	}
	
	public Noticia( Long id ) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getManchete() {
		return manchete;
	}

	public void setManchete(String manchete) {
		this.manchete = manchete;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public boolean equals(Object obj) {
		if( data == null || manchete == null ) return false;
		
		if( !(obj instanceof Noticia) )
			return false;
		
		Noticia other = (Noticia) obj;
		if( id != null && other.id != null && id.equals( other.id )){
			return true;
		}else if( data!=null && manchete!=null && data.getTime() == other.data.getTime() && manchete.equals( other.manchete ) ){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + ( this.id != null ? this.id.hashCode() : 0 );
		return hash;
	}
	
	@Override
	public String toString() {
		return "Noticia{" + "id=" + id + '}';
	}
	
}
