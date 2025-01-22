package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosResposta;
import acc.br.techflow.pedido.repository.PedidoRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarTodosPedidosServiceTest {

    @InjectMocks
    private ListarTodosPedidosService servicoTestado;

    @Mock
    private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Deve listar todos os pedidos com sucesso")
    void deveListarTodosPedidos() {
        List<Pedido> pedidos = Instancio.ofList(Pedido.class).create();

        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<ListarTodosPedidosResposta> resposta = servicoTestado.listar();

        verify(pedidoRepository).findAll();

        assertEquals(pedidos.size(), resposta.size());
        assertEquals(pedidos.get(0).getId(), resposta.get(0).getId());
        assertEquals(pedidos.get(0).getDataHora(), resposta.get(0).getDataHora());
        assertEquals(pedidos.get(0).getObservacao(), resposta.get(0).getObservacao());
        assertEquals(pedidos.get(0).getMetodoPagamento(), resposta.get(0).getMetodoPagamento());
        assertEquals(pedidos.get(0).getEndereco(), resposta.get(0).getEndereco());
        assertEquals(pedidos.get(0).getCliente().getId(), resposta.get(0).getClienteId());
    }
}