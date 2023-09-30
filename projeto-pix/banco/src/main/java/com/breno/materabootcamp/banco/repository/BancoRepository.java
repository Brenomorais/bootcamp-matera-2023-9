package com.breno.materabootcamp.banco.repository;

import com.breno.materabootcamp.banco.model.Banco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>{
	
    Optional<Banco> findByCodigo(int codigo);

}