package com.matera.bootcamp.repository;

import com.matera.bootcamp.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    /**
     * Formas de methodQuery palavras reservadas Keywords
     * get
     * find
     * stream
     * read
     * query
     */

    Conta findByNumeroConta(String numeroConta);
}
