package com.samuelmessias.desafio.dto;

import java.io.Serializable;

import com.samuelmessias.desafio.entities.Cidade;
import com.samuelmessias.desafio.entities.enums.Estado;

public class CidadeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private Estado estado;
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Long id, String nome, Estado estado) {
		this.id = id;
		this.nome = nome;
		this.estado = estado;
	}
	
	public CidadeDTO(Cidade entity) {
		id = entity.getId();
		nome = entity.getNome();
		estado = entity.getEstado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}	

}
