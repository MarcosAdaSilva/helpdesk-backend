package com.marcos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcos.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
