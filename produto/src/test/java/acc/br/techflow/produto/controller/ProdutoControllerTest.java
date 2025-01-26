package acc.br.techflow.produto.controller;

import acc.br.techflow.produto.entity.Estoque;
import acc.br.techflow.produto.entity.Produto;
import acc.br.techflow.produto.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoService service; // Mock do serviço para simular a lógica de negócios

    @InjectMocks
    private ProdutoController controller; // Injeta o mock no controlador

    private ObjectMapper objectMapper; // Serializador para JSON

    @BeforeEach
    void setUp() {
        // Inicializa os mocks e configura o MockMvc para testar o controlador
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Deve cadastrar um novo produto com estoque")
    @Test
    void cadastrarProduto() throws Exception {
        // Configuração do produto e do estoque
        Produto produto = new Produto();
        produto.setNome("Produto");
        produto.setDescricao("Descrição do Produto");
        produto.setValor(BigDecimal.valueOf(150.00));

        Estoque estoque = new Estoque();
        estoque.setQuantidade(50);
        produto.setEstoque(estoque);

        // Configura o comportamento do mock para salvar o produto
        when(service.salvarComEstoque(any(Produto.class))).thenAnswer(invocation -> {
            Produto savedProduto = invocation.getArgument(0);
            savedProduto.setId(1); // Simula o ID gerado pelo banco
            savedProduto.getEstoque().setId(1); // Simula o ID do estoque
            return savedProduto;
        });

        // Faz a requisição POST e valida o retorno
        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1)) // Verifica o ID gerado
                .andExpect(jsonPath("$.nome").value("Produto"))
                .andExpect(jsonPath("$.descricao").value("Descrição do Produto"))
                .andExpect(jsonPath("$.valor").value(150.00))
                .andExpect(jsonPath("$.estoque.quantidade").value(50));

        // Verifica se o serviço foi chamado uma vez
        verify(service, times(1)).salvarComEstoque(any(Produto.class));
    }

    @DisplayName("Deve listar todos os produtos")
    @Test
    void listarTodosProdutos() throws Exception {
        // Configuração de dois produtos com estoque
        Produto produto1 = new Produto(1, "Produto 1", "Descrição 1", BigDecimal.valueOf(100.00), new Estoque(1, 500, null));
        Produto produto2 = new Produto(2, "Produto 2", "Descrição 2", BigDecimal.valueOf(200.00), new Estoque(2, 300, null));

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        // Configura o mock para retornar a lista de produtos
        when(service.buscarTodos()).thenReturn(produtos);

        // Faz a requisição GET e valida o retorno
        mockMvc.perform(get("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))) // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica os detalhes do primeiro produto
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[0].descricao").value("Descrição 1"))
                .andExpect(jsonPath("$[0].valor").value(100.00))
                .andExpect(jsonPath("$[0].estoque.quantidade").value(500))
                .andExpect(jsonPath("$[1].id").value(2)) // Verifica os detalhes do segundo produto
                .andExpect(jsonPath("$[1].nome").value("Produto 2"))
                .andExpect(jsonPath("$[1].descricao").value("Descrição 2"))
                .andExpect(jsonPath("$[1].valor").value(200.00))
                .andExpect(jsonPath("$[1].estoque.quantidade").value(300));

        // Verifica se o serviço foi chamado uma vez
        verify(service, times(1)).buscarTodos();
    }

    @DisplayName("Deve consultar um produto por ID")
    @Test
    void consultarProdutoPorID() throws Exception {
        // Configuração do produto com estoque
        Produto produto = new Produto(1, "Produto", "Descrição", BigDecimal.valueOf(150.00), new Estoque(1, 100, null));

        // Configura o mock para retornar o produto
        when(service.consultarPorID(1L)).thenReturn(produto);

        // Faz a requisição GET e valida o retorno
        mockMvc.perform(get("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto"))
                .andExpect(jsonPath("$.descricao").value("Descrição"))
                .andExpect(jsonPath("$.valor").value(150.00))
                .andExpect(jsonPath("$.estoque.quantidade").value(100));

        // Verifica se o serviço foi chamado uma vez
        verify(service, times(1)).consultarPorID(1L);
    }

    @DisplayName("Deve atualizar um produto existente")
    @Test
    void atualizarProduto() throws Exception {
        // Configuração do produto atualizado
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(1);
        produtoAtualizado.setNome("Produto Atualizado");
        produtoAtualizado.setDescricao("Descrição Atualizada");
        produtoAtualizado.setValor(BigDecimal.valueOf(200.00));
        produtoAtualizado.setEstoque(new Estoque(1, 150, null));

        // Configura o mock para retornar o produto atualizado
        when(service.atualizar(eq(1L), any(Produto.class))).thenReturn(produtoAtualizado);

        // Faz a requisição PUT e valida o retorno
        mockMvc.perform(put("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.descricao").value("Descrição Atualizada"))
                .andExpect(jsonPath("$.valor").value(200.00))
                .andExpect(jsonPath("$.estoque.quantidade").value(150));

        // Verifica se o serviço foi chamado uma vez
        verify(service, times(1)).atualizar(eq(1L), any(Produto.class));
    }

    @DisplayName("Deve remover um produto pelo ID")
    @Test
    void removerProduto() throws Exception {
        // Configura o mock para não fazer nada ao remover
        doNothing().when(service).remover(1L);

        // Faz a requisição DELETE e valida o retorno
        mockMvc.perform(delete("/api/produtos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verifica se o serviço foi chamado uma vez
        verify(service, times(1)).remover(1L);
    }
}
