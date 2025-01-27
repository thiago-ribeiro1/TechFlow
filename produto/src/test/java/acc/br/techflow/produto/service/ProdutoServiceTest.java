package acc.br.techflow.produto.service;

import acc.br.techflow.produto.entity.Estoque;
import acc.br.techflow.produto.entity.Produto;
import acc.br.techflow.produto.repository.ProdutoRepository;
import acc.br.techflow.produto.repository.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    // Mock dos repositórios usados no ProdutoService
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    // Injeta os mocks no ProdutoService
    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Deve salvar um produto com estoque padrão")
    @Test
    void salvarProdutoComEstoquePadrao() {
        // Configura um novo produto sem estoque associado
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setDescricao("Descrição do Produto 1");
        produto.setValor(BigDecimal.valueOf(100.0));

        // Mocka o comportamento do produtoRepository para salvar o produto
        when(produtoRepository.save(produto)).thenAnswer(invocation -> {
            Produto savedProduto = invocation.getArgument(0);
            savedProduto.setId(1); // Simula o ID gerado pelo banco
            return savedProduto;
        });

        // Mocka o comportamento do estoqueRepository para salvar o estoque
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(invocation -> {
            Estoque estoque = invocation.getArgument(0);
            estoque.setId(1); // Simula o ID gerado pelo banco
            return estoque;
        });

        // Chama o método a ser testado
        Produto salvo = produtoService.salvarComEstoque(produto);

        // Verifica o resultado
        assertNotNull(salvo, "O produto salvo não deve ser nulo");
        assertEquals(1, salvo.getId(), "O ID do produto salvo deve ser 1");
        assertNotNull(salvo.getEstoque(), "O produto salvo deve ter um estoque associado");
        assertEquals(0, salvo.getEstoque().getQuantidade(), "A quantidade inicial do estoque deve ser 0");

        // Verifica as interações com os repositórios
        verify(produtoRepository, times(1)).save(produto);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @DisplayName("Deve salvar um produto com a quantidade de estoque informada")
    @Test
    void salvarProdutoComQuantidadeDeEstoqueInformada() {
        // Configuração do produto com estoque no formato fornecido
        Produto produto = new Produto();
        produto.setNome("Produto");
        produto.setDescricao("Descrição do Produto");
        produto.setValor(BigDecimal.valueOf(100.00));

        Estoque estoque = new Estoque();
        estoque.setQuantidade(50); // Quantidade estoque
        produto.setEstoque(estoque); // Associa o estoque ao produto

        // Mocka o comportamento do produtoRepository para salvar o produto
        when(produtoRepository.save(produto)).thenAnswer(invocation -> {
            Produto savedProduto = invocation.getArgument(0);
            savedProduto.setId(1); // Simula o ID gerado pelo banco
            return savedProduto;
        });

        // Mocka o comportamento do estoqueRepository para salvar o estoque
        when(estoqueRepository.save(any(Estoque.class))).thenAnswer(invocation -> {
            Estoque savedEstoque = invocation.getArgument(0);
            savedEstoque.setId(1); // Simula o ID gerado pelo banco
            return savedEstoque;
        });

        // Chama o método a ser testado
        Produto salvo = produtoService.salvarComEstoque(produto);

        // Verifica o resultado
        assertNotNull(salvo, "O produto salvo não deve ser nulo");
        assertEquals(1, salvo.getId(), "O ID do produto salvo deve ser 1");
        assertNotNull(salvo.getEstoque(), "O produto salvo deve ter um estoque associado");
        assertEquals(50, salvo.getEstoque().getQuantidade(), "A quantidade do estoque deve ser 50");

        // Verifica as interações com os repositórios
        verify(produtoRepository, times(1)).save(produto);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
    }

    @DisplayName("Deve retornar todos os produtos")
    @Test
    void buscarTodosProdutos() {
        // Configura uma lista de produtos simulada
        Produto produto1 = new Produto(1, "Produto 1", "Descrição 1", BigDecimal.valueOf(100.0), null);
        Produto produto2 = new Produto(2, "Produto 2", "Descrição 2", BigDecimal.valueOf(200.0), null);

        // Mocka o comportamento do produtoRepository para retornar a lista
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        // Chama o método a ser testado
        List<Produto> produtos = produtoService.buscarTodos();

        // Verifica o resultado
        assertNotNull(produtos, "A lista de produtos não deve ser nula");
        assertEquals(2, produtos.size(), "A lista deve conter 2 produtos");

        // Verifica que o método findAll foi chamado
        verify(produtoRepository, times(1)).findAll();
    }

    @DisplayName("Deve retornar um produto por ID")
    @Test
    void consultarPorIDProduto() {
        // Configura um produto existente
        Produto produto = new Produto(1, "Produto 1", "Descrição 1", BigDecimal.valueOf(100.0), null);

        // Mocka o comportamento do produtoRepository para encontrar o produto pelo ID
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // Chama o método a ser testado
        Produto encontrado = produtoService.consultarPorID(1L);

        // Verifica o resultado
        assertNotNull(encontrado, "O produto encontrado não deve ser nulo");
        assertEquals(1, encontrado.getId(), "O ID do produto encontrado deve ser 1");

        // Verifica que o método findById foi chamado
        verify(produtoRepository, times(1)).findById(1L);
    }

    @DisplayName("Deve lançar exceção ao tentar consultar produto inexistente por ID")
    @Test
    void consultarPorIDProdutoInexistente() {
        // Mocka o comportamento do produtoRepository para retornar vazio
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        // Chama o método a ser testado e espera uma exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> produtoService.consultarPorID(1L));
        assertEquals("Produto não encontrado", exception.getMessage(), "A mensagem da exceção deve ser 'Produto não encontrado'");

        // Verifica que o método findById foi chamado
        verify(produtoRepository, times(1)).findById(1L);
    }

    @DisplayName("Deve atualizar um produto existente")
    @Test
    void atualizarProdutoExistente() {
        // Configura um produto existente com estoque
        Produto existente = new Produto(1, "Produto 1", "Descrição 1", BigDecimal.valueOf(100.0), new Estoque(1, 10, null));

        // Configura um produto atualizado
        Produto atualizado = new Produto(null, "Produto Atualizado", "Descrição Atualizada", BigDecimal.valueOf(200.0), new Estoque(null, 20, null));

        // Mocka o comportamento do produtoRepository para encontrar o produto e salvar as alterações
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(produtoRepository.save(any(Produto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chama o método a ser testado
        Produto resultado = produtoService.atualizar(1L, atualizado);

        // Verifica o resultado
        assertNotNull(resultado, "O produto atualizado não deve ser nulo");
        assertEquals("Produto Atualizado", resultado.getNome(), "O nome do produto deve ser atualizado");
        assertEquals("Descrição Atualizada", resultado.getDescricao(), "A descrição do produto deve ser atualizada");
        assertEquals(BigDecimal.valueOf(200.0), resultado.getValor(), "O valor do produto deve ser atualizado");
        assertEquals(20, resultado.getEstoque().getQuantidade(), "A quantidade do estoque deve ser atualizada");

        // Verifica as interações com os repositórios
        verify(produtoRepository, times(1)).findById(1L);
        verify(estoqueRepository, times(1)).save(any(Estoque.class));
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @DisplayName("Deve remover um produto pelo ID")
    @Test
    void removerProduto() {
        // Mocka o comportamento do produtoRepository para deletar o produto
        doNothing().when(produtoRepository).deleteById(1L);

        // Chama o método a ser testado
        produtoService.remover(1L);

        // Verifica que o método deleteById foi chamado
        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
