package acc.br.techflow.cliente.service;

import acc.br.techflow.cliente.entity.Cliente;
import acc.br.techflow.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @DisplayName("Deve salvar o cliente quando os dados forem válidos")
    @Test
    void salvarClienteValido() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("12345678901");

        when(repository.save(cliente)).thenReturn(cliente);

        Cliente salvo = service.salvar(cliente);

        assertNotNull(salvo, "O cliente salvo não deve ser nulo");
        assertEquals("João", salvo.getNome(), "O nome do cliente salvo deve ser João");
        assertEquals("12345678901", salvo.getCpf(), "O CPF do cliente salvo deve ser 12345678901");
        verify(repository, times(1)).save(cliente);
        verifyNoMoreInteractions(repository);
    }

    @DisplayName("Deve retornar uma lista de clientes")
    @Test
    void buscarTodosClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("João");
        cliente1.setCpf("12345678901");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Maria");
        cliente2.setCpf("12345678902");

        when(repository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> clientes = service.buscarTodos();

        assertNotNull(clientes);
        assertEquals(2, clientes.size());
        verify(repository, times(1)).findAll();
    }

    @DisplayName("Deve retornar um cliente encontrado pelo id")
    @Test
    void consultarPorIDCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João");
        cliente.setCpf("12345678901");

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente encontrado = service.consultarPorID(1L);

        assertNotNull(encontrado);
        assertEquals(1, encontrado.getId());
        assertEquals("João", encontrado.getNome());
        assertEquals("12345678901", encontrado.getCpf());
        verify(repository, times(1)).findById(1L);
    }

    @DisplayName("Deve lançar uma exceção quando o cliente não for encontrado")
    @Test
    void consultarPorID_Exception() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.consultarPorID(1L));

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @DisplayName("Deve atualiar um cliente com novos dados")
    @Test
    void atualizarClienteExistente() {
        Cliente existente = new Cliente();
        existente.setId(1);
        existente.setNome("João");
        existente.setCpf("12345678901");

        Cliente atualizado = new Cliente();
        atualizado.setNome("Matheus");
        atualizado.setCpf("98765432100");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Cliente.class))).thenReturn(atualizado);

        Cliente resultado = service.atualizar(1L, atualizado);

        assertNotNull(resultado);
        assertEquals("Matheus", resultado.getNome());
        assertEquals("98765432100", resultado.getCpf());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existente);
    }

    @DisplayName("Deve remover um cliente pelo id")
    @Test
    void removerCliente() {
        doNothing().when(repository).deleteById(1L);

        service.remover(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}

