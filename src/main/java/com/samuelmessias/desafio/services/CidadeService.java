package com.samuelmessias.desafio.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samuelmessias.desafio.dto.CidadeDTO;
import com.samuelmessias.desafio.entities.Cidade;
import com.samuelmessias.desafio.repositories.CidadeRepository;
import com.samuelmessias.desafio.services.exceptions.DataBaseException;
import com.samuelmessias.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;	
	
	@Transactional(readOnly = true)
	public Page<CidadeDTO> findAll(Pageable pageable){
		Page<Cidade> list = repository.findAll(pageable);
		return list.map(x -> new CidadeDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<CidadeDTO> findByNome(String nome, Pageable pageable){
		Page<Cidade> list = repository.findByNome(nome, pageable);
		return list.map(x -> new CidadeDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Page<CidadeDTO> findByEstado(String estado, Pageable pageable){
		Page<Cidade> list = repository.findByEstado(estado, pageable);
		return list.map(x -> new CidadeDTO(x));
	}
	
	@Transactional(readOnly = true)
	public Cidade findById(Long id){
		Optional<Cidade> obj = repository.findById(id);
		Cidade entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Entidade não encontrada!"));
		return entity;
	}
	
	
	@Transactional
	public CidadeDTO insert(CidadeDTO dto) {
		Cidade entity = new Cidade();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CidadeDTO(entity);
	}
	
	@Transactional
	public CidadeDTO update(Long id, CidadeDTO dto) {
		try {
			Cidade entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new CidadeDTO(entity);
			
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integridade do banco violada");
		}
	}
	
	private void copyDtoToEntity(CidadeDTO dto, Cidade entity) {
		entity.setNome(dto.getNome());
		entity.setEstado(dto.getEstado());
	}
	
	
}
