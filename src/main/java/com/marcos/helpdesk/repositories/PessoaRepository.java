package com.marcos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
