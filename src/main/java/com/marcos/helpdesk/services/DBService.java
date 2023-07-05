package com.marcos.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcos.helpdesk.domain.Chamado;
import com.marcos.helpdesk.domain.Cliente;
import com.marcos.helpdesk.domain.Tecnico;
import com.marcos.helpdesk.repositories.ChamadoRepository;
import com.marcos.helpdesk.repositories.PessoaRepository;

import enums.Perfil;
import enums.Prioridade;
import enums.Status;

@Service
public class DBService {

	@Autowired
	private ChamadoRepository chamadorepository;
	@Autowired
	private PessoaRepository pessoaRepository;

	public void instanciaDB() {
		
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "636.532.302.68", "valdir@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Richard Atallman", "903.347.070-56", "stallman@mail.com", "123");
		Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannonr", "271.068.470-54", "shannon@mail.com", "123");
		Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com", "123");
		Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com", "123");
		
		Cliente cli1 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com", "123");
		Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@mail.com", "123");
		Cliente cli3 = new Cliente(null, "Charlie Darwin", "792.043.830-62", "darwin@mail.com", "123");
		Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com", "123");
		Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@mail.com", "123");

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Teste chamado 1", tec1,
				cli1);
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 02", "Teste chamado 2", tec1,
				cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 03", "Teste chamado 4", tec2,
				cli3);
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 04", "Teste chamado", tec3,
				cli3);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 05", "Teste chamado 5", tec2,
				cli1);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 06", "Teste chamado 6", tec1,
				cli5);

		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
		chamadorepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));

	}

}
