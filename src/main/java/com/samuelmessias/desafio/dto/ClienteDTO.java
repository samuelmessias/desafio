package com.samuelmessias.desafio.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.samuelmessias.desafio.entities.Cliente;
import com.samuelmessias.desafio.entities.enums.Sexo;

public class ClienteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;	
	
	private Long id;
	private String nome;
	private Sexo sexo;
	private LocalDate dataNascimento;	
	private CidadeDTO cidade;
	
	public ClienteDTO() {
		
	}
	
	
	public ClienteDTO(Long id, String nome, Sexo sexo, LocalDate dataNascimento, CidadeDTO cidade) {
		this.id = id;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.cidade = cidade;
	}
	
	public ClienteDTO(Cliente entity) {
		id = entity.getId();
		nome = entity.getNome();
		sexo = entity.getSexo();
		dataNascimento = entity.getDataNascimento();
		cidade = new CidadeDTO(entity.getCidade());
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

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public CidadeDTO getCidade() {
		return cidade;
	}

	public void setCidade(CidadeDTO cidade) {
		this.cidade = cidade;
	}
	
	public int getIdade() {
		LocalDate dataAtual = LocalDate.now();
	return dataAtual.getYear() - this.dataNascimento.getYear();
	}
	

}
