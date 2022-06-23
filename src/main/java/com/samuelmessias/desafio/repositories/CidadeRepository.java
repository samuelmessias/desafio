package com.samuelmessias.desafio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.samuelmessias.desafio.entities.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	@Query("SELECT obj FROM Cidade obj WHERE UPPER(obj.nome) LIKE UPPER(CONCAT('%',:nome,'%')) ORDER BY obj.nome ASC ")
	Page<Cidade> findByNome(String nome, Pageable pageable);
	
	@Query("SELECT obj FROM Cidade obj WHERE UPPER(obj.estado) = UPPER(:estado) ORDER BY obj.nome ASC ")	
	Page<Cidade> findByEstado(String estado, Pageable pageable);

}
