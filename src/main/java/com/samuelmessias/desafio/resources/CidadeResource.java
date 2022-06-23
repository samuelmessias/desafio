package com.samuelmessias.desafio.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samuelmessias.desafio.dto.CidadeDTO;
import com.samuelmessias.desafio.services.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeService service;
	
	@GetMapping
	public ResponseEntity<Page<CidadeDTO>> findAll(Pageable pageable){
		Page<CidadeDTO> list = service.findAll(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping(value = "/consulta_por_nome")
	public ResponseEntity<Page<CidadeDTO>> findByNome(@RequestParam(value = "nome", defaultValue = "") String nome, Pageable pageable){
		Page<CidadeDTO> list = service.findByNome(nome, pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/consulta_por_estado")
	public ResponseEntity<Page<CidadeDTO>> findByEstado(@RequestParam(value = "estado", defaultValue = "") String estado, Pageable pageable){
		Page<CidadeDTO> list = service.findByEstado(estado, pageable);
		return ResponseEntity.ok().body(list);
	}
	
	
	@PostMapping
	public ResponseEntity<CidadeDTO> insert(@RequestBody CidadeDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CidadeDTO> update(@PathVariable Long id, @RequestBody CidadeDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
