package acc.br.techflow.cliente.controller;

import acc.br.techflow.cliente.entity.Cliente;
import acc.br.techflow.cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService service;

    @InjectMocks
    private ClienteController controller;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Deve cadastrar um novo cliente")
    @Test
    void cadastrarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("12345678901");

        when(service.salvar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes") // POST
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));

        verify(service, times(1)).salvar(any(Cliente.class));
    }

    @DisplayName("Deve listar todos os clientes")
    @Test
    void listarTodosClientes() throws Exception {
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNome("João");
        cliente1.setCpf("12345678901");

        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Maria");
        cliente2.setCpf("98765432100");

        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(service.buscarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes") // GET
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L)) // Verifica o ID do primeiro cliente
                .andExpect(jsonPath("$[0].nome").value("João")) // Verifica o nome do primeiro cliente
                .andExpect(jsonPath("$[0].cpf").value("12345678901")) // Verifica o CPF do primeiro cliente
                .andExpect(jsonPath("$[1].id").value(2L)) // Verifica o ID do segundo cliente
                .andExpect(jsonPath("$[1].nome").value("Maria")) // Verifica o nome do segundo cliente
                .andExpect(jsonPath("$[1].cpf").value("98765432100")); // Verifica o CPF do segundo cliente

        verify(service, times(1)).buscarTodos();
    }

    @DisplayName("Deve consultar um cliente por ID")
    @Test
    void consultarClientePorID() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNome("Maria");
        cliente.setCpf("98765432100");

        when(service.consultarPorID(2L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/2") // GET
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.cpf").value("98765432100"));

        verify(service, times(1)).consultarPorID(2L);
    }

    @DisplayName("Deve atualizar um cliente existente")
    @Test
    void atualizarCliente() throws Exception {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1); // atualiza o cliente João para Matheus
        clienteAtualizado.setNome("Matheus");
        clienteAtualizado.setCpf("56898745612");

        when(service.atualizar(eq(1L), any(Cliente.class))).thenReturn(clienteAtualizado);

        mockMvc.perform(put("/api/clientes/1") // PUT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Matheus"))
                .andExpect(jsonPath("$.cpf").value("56898745612"));

        verify(service, times(1)).atualizar(eq(1L), any(Cliente.class));
    }

    @DisplayName("Deve remover um cliente pelo ID")
    @Test
    void removerCliente() throws Exception {
        doNothing().when(service).remover(1L);

        mockMvc.perform(delete("/api/clientes/1") // DELETE
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service, times(1)).remover(1L);
    }
}
