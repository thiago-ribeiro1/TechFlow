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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Testa atualização do estoque")
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
    @DisplayName("Testa processamento do pedido no estoque")
    void testProcessarPedidoEstoque() {
        Mockito.when(estoqueRepository.findByProdutoId(1)).thenReturn(Optional.of(estoqueProduto1));
        Mockito.when(estoqueRepository.findByProdutoId(2)).thenReturn(Optional.of(estoqueProduto2));

        List<Integer> produtoIds = Arrays.asList(1, 2);
        List<Integer> quantidades = Arrays.asList(5, 5);

        estoqueService.processarPedidoEstoque(produtoIds, quantidades);

        assertEquals(5, estoqueProduto1.getQuantidade());
        assertEquals(5, estoqueProduto2.getQuantidade());

        verify(estoqueRepository, Mockito.times(1)).save(estoqueProduto1);
        verify(estoqueRepository, Mockito.times(1)).save(estoqueProduto2);
    }

    @Test
    @DisplayName("Testa a construção da lista de itens do pedido a partir de produtoId e quantidade")
    public void testBuildItensPedido() {
        List<Integer> produtoIds = Arrays.asList(1, 2, 3);
        List<Integer> quantidades = Arrays.asList(5, 10, 15);

        List<ItemPedidoRabbitMQDTO> itensPedido = estoqueService.buildItensPedido(produtoIds, quantidades);

        assertEquals(new ItemPedidoRabbitMQDTO(1, 5), itensPedido.get(0));
        assertEquals(new ItemPedidoRabbitMQDTO(2, 10), itensPedido.get(1));
        assertEquals(new ItemPedidoRabbitMQDTO(3, 15), itensPedido.get(2));
    }

}
