package com.samuelmessias.desafio.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.samuelmessias.desafio.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	@Query("SELECT obj FROM Cliente obj WHERE UPPER(obj.nome) LIKE UPPER(CONCAT('%',:nome,'%')) ORDER BY obj.nome ASC ")
	Page<Cliente> findByNome(String nome, Pageable pageable);
}
