package com.breno.materabootcamp.banco.repository;

import com.breno.materabootcamp.banco.model.Titular;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TitularRepository extends JpaRepository<Titular, Long>{

	Optional<Titular> findByCpf(String cpf);

}