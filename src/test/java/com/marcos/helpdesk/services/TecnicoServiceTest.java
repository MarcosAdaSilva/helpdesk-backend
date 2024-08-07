package com.marcos.helpdesk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.marcos.helpdesk.domain.Chamado;
import com.marcos.helpdesk.domain.Tecnico;
import com.marcos.helpdesk.domain.dtos.TecnicoDTO;
import com.marcos.helpdesk.repositories.PessoaRepository;
import com.marcos.helpdesk.repositories.TecnicoRepository;
import com.marcos.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcos.helpdesk.services.exceptions.ObjectnotFoundException;

@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    @InjectMocks
    private TecnicoService service;

    @Mock
    private TecnicoRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    private Tecnico tecnico;
    private TecnicoDTO tecnicoDTO;

    @BeforeEach
    void setUp() {
        tecnico = new Tecnico();
        tecnico.setId(1);
        tecnico.setNome("John Doe");
        tecnico.setCpf("12345678900");
        tecnico.setEmail("john.doe@example.com");
        tecnico.setSenha("12345");
        tecnico.setChamados(new ArrayList<>()); // Inicializa a lista de chamados

        tecnicoDTO = new TecnicoDTO();
        tecnicoDTO.setId(1);
        tecnicoDTO.setNome("John Doe");
        tecnicoDTO.setCpf("12345678900");
        tecnicoDTO.setEmail("john.doe@example.com");
        tecnicoDTO.setSenha("12345");
    }

    @Test
    void testFindById() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(tecnico));

        Tecnico found = service.findById(1);

        assertNotNull(found);
        assertEquals(Tecnico.class, found.getClass());
        assertEquals(1, found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ObjectnotFoundException.class, () -> {
            service.findById(1);
        });

        assertEquals("Objeto não encontrado! Id: 1", exception.getMessage());
    }

    @Test
    void testFindAll() {
        List<Tecnico> list = new ArrayList<>();
        list.add(tecnico);

        when(repository.findAll()).thenReturn(list);

        List<Tecnico> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Tecnico.class, result.get(0).getClass());
    }

    @Test
    void testCreate() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("12345");
        when(repository.save(any(Tecnico.class))).thenReturn(tecnico);

        Tecnico created = service.create(tecnicoDTO);

        assertNotNull(created);
        assertEquals(Tecnico.class, created.getClass());
        assertEquals(1, created.getId());
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(tecnico));
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Tecnico.class))).thenReturn(tecnico);

        Tecnico updated = service.update(1, tecnicoDTO);

        assertNotNull(updated);
        assertEquals(Tecnico.class, updated.getClass());
        assertEquals(1, updated.getId());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(tecnico));

        service.delete(1);

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteWithOrders() {
        Chamado chamado = new Chamado(); // Cria um chamado fictício
        tecnico.getChamados().add(chamado); // Adiciona o chamado ao técnico

        when(repository.findById(anyInt())).thenReturn(Optional.of(tecnico));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            service.delete(1);
        });

        assertEquals("Técnico possui ordens de serviço e não pode ser deletado!", exception.getMessage());
    }

    

   
}
