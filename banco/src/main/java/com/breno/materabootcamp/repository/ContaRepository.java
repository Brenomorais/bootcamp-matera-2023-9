package com.breno.materabootcamp.repository;

import com.breno.materabootcamp.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    /**
     * Formas de methodQuery palavras reservadas Keywords
     * get
     * find
     * stream
     * read
     * query
     */
    Conta agencia(String agencia);

    Conta findByAgencia(String agencia);

    Conta findByNumeroConta(String numeroConta);

    /* query jpql */
    @Query("select c from Conta c order by c.numeroConta asc")
    Conta buscarContasOrdenadas();

    /* query jpql com parametro */
    @Query("select c from Conta c where c.agencia = :agencia")
    List<Conta> buscarContaPorAgencia(@Param("agencia") String agenciaComOutroNome);

    /* query nativa sql com parametro */
    @Query(value = "select * from CONTA c where c.agencia = ?1", nativeQuery = true)
    List<Conta> buscarContaPorAgenciaComQueryNativa(String agenciaComOutroNome);

    //Busca chave cpf no titular da conta
	Conta findByTitularCpf(String  cpf);

}