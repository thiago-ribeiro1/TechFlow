package acc.br.techflow.estoque.service;
import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.repository.EstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class EstoqueServiceTest {

    @InjectMocks
    private EstoqueService estoqueService;

    @Mock
    private EstoqueRepository estoqueRepository;

    private Estoque estoqueProduto1;
    private Estoque estoqueProduto2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        estoqueProduto1 = new Estoque(1, 10, 1);
        estoqueProduto2 = new Estoque(2, 10, 2);
    }

    @Test
    @DisplayName("Deve verificar se o estoque é atualizado")
    void testAtualizarEstoque() {
        Mockito.when(estoqueRepository.findByProdutoId(1)).thenReturn(Optional.of(estoqueProduto1));
        Mockito.when(estoqueRepository.findByProdutoId(2)).thenReturn(Optional.of(estoqueProduto2));

        ItemPedidoRabbitMQDTO item1 = new ItemPedidoRabbitMQDTO(1, 5);
        ItemPedidoRabbitMQDTO item2 = new ItemPedidoRabbitMQDTO(2, 5);
        List<ItemPedidoRabbitMQDTO> itensPedido = Arrays.asList(item1, item2);

        estoqueService.atualizarEstoque(itensPedido);

        assertEquals(5, estoqueProduto1.getQuantidade());
        assertEquals(5, estoqueProduto2.getQuantidade());

        verify(estoqueRepository, Mockito.times(1)).save(estoqueProduto1);
        verify(estoqueRepository, Mockito.times(1)).save(estoqueProduto2);
    }

    @Test
    @DisplayName("Deve testar a validação do estoque com quantidade suficiente")
    void testValidarEstoqueComQuantidadeSuficiente() {
        ItemPedidoRabbitMQDTO item1 = new ItemPedidoRabbitMQDTO(1, 5);
        ItemPedidoRabbitMQDTO item2 = new ItemPedidoRabbitMQDTO(2, 5);
        List<ItemPedidoRabbitMQDTO> itensPedido = Arrays.asList(item1, item2);

        Mockito.when(estoqueRepository.findByProdutoId(1)).thenReturn(Optional.of(estoqueProduto1));
        Mockito.when(estoqueRepository.findByProdutoId(2)).thenReturn(Optional.of(estoqueProduto2));

        Boolean resultado = estoqueService.validar(itensPedido);
        assertTrue(resultado);
    }
    //Cenários negativos
    @Test
    @DisplayName("Deve testar a validação do estoque com quantidade insuficiente")
    void testValidarEstoqueComQuantidadeInsuficiente() {
        ItemPedidoRabbitMQDTO item1 = new ItemPedidoRabbitMQDTO(1, 10);
        ItemPedidoRabbitMQDTO item2 = new ItemPedidoRabbitMQDTO(2, 20);
        List<ItemPedidoRabbitMQDTO> itensPedido = Arrays.asList(item1, item2);

        Mockito.when(estoqueRepository.findByProdutoId(1)).thenReturn(Optional.of(estoqueProduto1));
        Mockito.when(estoqueRepository.findByProdutoId(2)).thenReturn(Optional.of(estoqueProduto2));

        Boolean resultado = estoqueService.validar(itensPedido);
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o produto for inválido no estoque")
    void testAtualizarEstoqueComProdutoInvalido() {
        ItemPedidoRabbitMQDTO itemInvalido = new ItemPedidoRabbitMQDTO(3, 5);

        Mockito.when(estoqueRepository.findByProdutoId(3)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            estoqueService.atualizarEstoque(Arrays.asList(itemInvalido));
        });

        assertEquals("Produto inválido", exception.getMessage());
        verify(estoqueRepository, Mockito.times(1)).findByProdutoId(3);
    }
}