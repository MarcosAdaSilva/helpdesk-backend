package com.marcos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
