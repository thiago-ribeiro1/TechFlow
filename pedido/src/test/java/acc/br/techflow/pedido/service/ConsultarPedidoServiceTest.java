package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dominio.StatusPedido;
import acc.br.techflow.pedido.dto.resposta.ConsultarPedidoResposta;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.ItemPedidoRepository;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarPedidoServiceTest {

    @InjectMocks
    private ConsultarPedidoService servicoTestado;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private StatusPedidoRepository statusPedidoRepository;

    @Test
    @DisplayName("Deve retornar os dados do pedido com sucesso")
    void deveRetornarDadosPedido() {
        Integer pedidoId = 1;
        Pedido pedido = Instancio.of(Pedido.class).create();
        List<ItemPedido> itensPedido = Instancio.ofList(ItemPedido.class).create();
        List<StatusPedido> statusPedido = Instancio.ofList(StatusPedido.class).create();

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.ofNullable(pedido));
        when(itemPedidoRepository.findAllByPedidoId(pedidoId)).thenReturn(itensPedido);
        when(statusPedidoRepository.findAllByPedidoId(pedidoId)).thenReturn(statusPedido);

        ConsultarPedidoResposta resposta = servicoTestado.consultar(pedidoId);

        verify(pedidoRepository).findById(pedidoId);
        verify(itemPedidoRepository).findAllByPedidoId(pedidoId);
        verify(statusPedidoRepository).findAllByPedidoId(pedidoId);

        assertNotNull(resposta);
        assertEquals(pedido.getId(), resposta.getPedidoId());
    }

    @Test
    @DisplayName("Deve retornar erro quando ID do pedido não encontrado no banco de dados")
    void deveRetornarErroQuandoPedidoNaEncontrado() {
        Integer pedidoId = 1;
        Pedido pedido = Instancio.of(Pedido.class).create();

        doThrow(DadoNaoEncontradoException.class)
                .when(pedidoRepository).findById(pedidoId);

        assertThrows(DadoNaoEncontradoException.class, () -> servicoTestado.consultar(pedidoId));

        verify(pedidoRepository).findById(pedidoId);
        verify(itemPedidoRepository, never()).findAllByPedidoId(pedidoId);
        verify(statusPedidoRepository, never()).findAllByPedidoId(pedidoId);
    }
}