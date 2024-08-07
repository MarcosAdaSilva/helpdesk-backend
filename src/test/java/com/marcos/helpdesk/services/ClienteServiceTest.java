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
import com.marcos.helpdesk.domain.Cliente;
import com.marcos.helpdesk.domain.dtos.ClienteDTO;
import com.marcos.helpdesk.repositories.ClienteRepository;
import com.marcos.helpdesk.repositories.PessoaRepository;
import com.marcos.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcos.helpdesk.services.exceptions.ObjectnotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("John Doe");
        cliente.setCpf("12345678900");
        cliente.setEmail("john.doe@example.com");
        cliente.setSenha("12345");
        cliente.setChamados(new ArrayList<>()); // Inicializa a lista de chamados

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1);
        clienteDTO.setNome("John Doe");
        clienteDTO.setCpf("12345678900");
        clienteDTO.setEmail("john.doe@example.com");
        clienteDTO.setSenha("12345");
    }

    @Test
    void testFindById() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(cliente));

        Cliente found = service.findById(1);

        assertNotNull(found);
        assertEquals(Cliente.class, found.getClass());
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
        List<Cliente> list = new ArrayList<>();
        list.add(cliente);

        when(repository.findAll()).thenReturn(list);

        List<Cliente> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Cliente.class, result.get(0).getClass());
    }

    @Test
    void testCreate() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("12345");
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente created = service.create(clienteDTO);

        assertNotNull(created);
        assertEquals(Cliente.class, created.getClass());
        assertEquals(1, created.getId());
    }

    @Test
    void testUpdate() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(cliente));
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updated = service.update(1, clienteDTO);

        assertNotNull(updated);
        assertEquals(Cliente.class, updated.getClass());
        assertEquals(1, updated.getId());
    }

    @Test
    void testDelete() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(cliente));

        service.delete(1);

        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteWithOrders() {
        Chamado chamado = new Chamado(); // Cria um chamado fictício
        cliente.getChamados().add(chamado); // Adiciona o chamado ao cliente

        when(repository.findById(anyInt())).thenReturn(Optional.of(cliente));

        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            service.delete(1);
        });

        assertEquals("Cliente possui ordens de serviço e não pode ser deletado!", exception.getMessage());
    }

}
