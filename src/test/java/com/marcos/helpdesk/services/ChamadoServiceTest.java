package com.marcos.helpdesk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.marcos.helpdesk.domain.Chamado;
import com.marcos.helpdesk.domain.Cliente;
import com.marcos.helpdesk.domain.Tecnico;
import com.marcos.helpdesk.domain.dtos.ChamadoDTO;
import com.marcos.helpdesk.repositories.ChamadoRepository;
import com.marcos.helpdesk.services.exceptions.ObjectnotFoundException;

@SpringBootTest
public class ChamadoServiceTest {

    @InjectMocks
    private ChamadoService chamadoService;

    @Mock
    private ChamadoRepository chamadoRepository;

    @Mock
    private TecnicoService tecnicoService;

    @Mock
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Chamado chamado = new Chamado();
        when(chamadoRepository.findById(1)).thenReturn(Optional.of(chamado));

        Chamado foundChamado = chamadoService.findById(1);
        assertEquals(chamado, foundChamado);
    }

    @Test
    public void testFindByIdNotFound() {
        when(chamadoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ObjectnotFoundException.class, () -> {
            chamadoService.findById(1);
        });
    }

    @Test
    public void testFindAll() {
        Chamado chamado1 = new Chamado();
        Chamado chamado2 = new Chamado();
        when(chamadoRepository.findAll()).thenReturn(Arrays.asList(chamado1, chamado2));

        List<Chamado> chamados = chamadoService.findAll();
        assertEquals(2, chamados.size());
        assertEquals(chamado1, chamados.get(0));
        assertEquals(chamado2, chamados.get(1));
    }

    @Test
    public void testCreate() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        Chamado chamado = new Chamado();
        when(tecnicoService.findById(1)).thenReturn(new Tecnico());
        when(clienteService.findById(1)).thenReturn(new Cliente());
        when(chamadoRepository.save(any(Chamado.class))).thenReturn(chamado);

        Chamado createdChamado = chamadoService.create(chamadoDTO);
        assertEquals(chamado, createdChamado);
    }

    @Test
    public void testUpdate() {
        ChamadoDTO chamadoDTO = new ChamadoDTO();
        Chamado existingChamado = new Chamado();
        Chamado updatedChamado = new Chamado();

        when(chamadoRepository.findById(1)).thenReturn(Optional.of(existingChamado));
        when(tecnicoService.findById(1)).thenReturn(new Tecnico());
        when(clienteService.findById(1)).thenReturn(new Cliente());
        when(chamadoRepository.save(any(Chamado.class))).thenReturn(updatedChamado);

        Chamado result = chamadoService.update(1, chamadoDTO);
        assertEquals(updatedChamado, result);
    }
}
