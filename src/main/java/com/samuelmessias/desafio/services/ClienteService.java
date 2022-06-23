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

import com.samuelmessias.desafio.dto.ClienteDTO;
import com.samuelmessias.desafio.entities.Cliente;
import com.samuelmessias.desafio.repositories.ClienteRepository;
import com.samuelmessias.desafio.services.exceptions.DataBaseException;
import com.samuelmessias.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAll(Pageable pageable){
		Page<Cliente> list = repository.findAll(pageable);
		return list.map(x -> new ClienteDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id){
		Optional<Cliente> obj = repository.findById(id);
		Cliente entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Entidade não encontrada!"));
		return new ClienteDTO(entity);
	}
	
	
	@Transactional(readOnly = true)
	public Page<ClienteDTO> findByNome(String nome, Pageable pageable){
		Page<Cliente> list = repository.findByNome(nome, pageable);
		return list.map(x -> new ClienteDTO(x));
	}
		
	
	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
		Cliente entity = new Cliente();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClienteDTO(entity);
	}
	
	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {
		try {
			Cliente entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClienteDTO(entity);
			
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
	
	private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
		entity.setNome(dto.getNome());
		entity.setDataNascimento(dto.getDataNascimento());
		entity.setSexo(dto.getSexo());
		entity.setCidade(cidadeService.findById(dto.getCidade().getId()));
	}
	
	
}
