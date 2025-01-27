package acc.br.techflow.produto.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @DisplayName("Deve criar um produto sem ID inicial")
    @Test
    void criarProdutoSemID() {
        // Configuração do produto
        Produto produto = new Produto();
        produto.setNome("Produto");
        produto.setDescricao("Descrição do Produto");
        produto.setValor(BigDecimal.valueOf(99.99));

        // Verificações
        assertNotNull(produto, "O produto não deve ser nulo");
        assertNull(produto.getId(), "O ID do produto deve ser nulo inicialmente, pois será gerado pelo banco");
        assertEquals("Produto", produto.getNome(), "O nome do produto deve ser 'Produto'");
        assertEquals("Descrição do Produto", produto.getDescricao(), "A descrição do produto deve ser 'Descrição do Produto'");
        assertEquals(BigDecimal.valueOf(99.99), produto.getValor(), "O valor do produto deve ser 99.99");
    }

    @DisplayName("Deve configurar o estoque associado ao produto")
    @Test
    void configurarEstoqueAssociadoAoProduto() {
        // Configuração do produto
        Produto produto = new Produto();
        produto.setNome("Produto Teste");

        // Configuração do estoque
        Estoque estoque = new Estoque();
        estoque.setQuantidade(100);

        // Associa o estoque ao produto
        produto.setEstoque(estoque);

        // Verificações
        assertNotNull(produto.getEstoque(), "O produto deve ter um estoque associado");
        assertEquals(100, produto.getEstoque().getQuantidade(), "A quantidade do estoque deve ser 100");
        assertEquals(produto, estoque.getProduto(), "O estoque deve estar associado ao produto");
    }

    @DisplayName("Deve atualizar o estoque associado ao produto")
    @Test
    void atualizarEstoqueAssociadoAoProduto() {
        // Configuração do produto
        Produto produto = new Produto();
        produto.setNome("Produto Teste");

        // Configuração inicial do estoque
        Estoque estoqueInicial = new Estoque();
        estoqueInicial.setQuantidade(100);

        // Associa o estoque inicial ao produto
        produto.setEstoque(estoqueInicial);

        // Configuração de um novo estoque
        Estoque novoEstoque = new Estoque();
        novoEstoque.setQuantidade(200);

        // Atualiza o estoque do produto
        produto.setEstoque(novoEstoque);

        // Verificações
        assertNotNull(produto.getEstoque(), "O produto deve ter um estoque associado");
        assertEquals(200, produto.getEstoque().getQuantidade(), "A quantidade do estoque deve ser 200");
        assertEquals(produto, novoEstoque.getProduto(), "O novo estoque deve estar associado ao produto");
    }
}
