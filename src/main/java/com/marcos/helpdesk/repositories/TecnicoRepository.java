package com.marcos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
