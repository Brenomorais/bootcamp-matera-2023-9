package com.brenom.bootcampmatera.bacen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brenom.bootcampmatera.bacen.model.dto.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{
	
	Conta findByChavePix(String chavePix);

}
