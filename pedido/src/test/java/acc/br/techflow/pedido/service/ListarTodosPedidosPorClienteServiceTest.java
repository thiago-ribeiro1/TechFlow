package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosPorClienteResposta;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.service.consultar.ConsultarClienteService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarTodosPedidosPorClienteServiceTest {

    @InjectMocks
    private ListarTodosPedidosPorClienteService servicoTestado;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ConsultarClienteService consultarClienteService;

    @Test
    @DisplayName("Deve listar todos os pedidos do cliente com sucesso")
    void deveListarTodosPedidosDoCliente() {
        Integer clienteId = 1;
        List<Pedido> pedidos = Instancio.ofList(Pedido.class).create();

        when(pedidoRepository.findAllByClienteId(clienteId)).thenReturn(pedidos);

        List<ListarTodosPedidosPorClienteResposta> resposta = servicoTestado.listar(clienteId);

        verify(consultarClienteService).validarPorId(clienteId);
        verify(pedidoRepository).findAllByClienteId(clienteId);

        assertEquals(pedidos.size(), resposta.size());
        assertEquals(pedidos.get(0).getId(), resposta.get(0).getPedidoId());
        assertEquals(pedidos.get(0).getDataHora(), resposta.get(0).getDataHora());
        assertEquals(pedidos.get(0).getObservacao(), resposta.get(0).getObservacao());
        assertEquals(pedidos.get(0).getMetodoPagamento(), resposta.get(0).getMetodoPagamento());
        assertEquals(pedidos.get(0).getEndereco(), resposta.get(0).getEndereco());
        assertEquals(pedidos.get(0).getCliente().getId(), resposta.get(0).getClienteId());
    }

    @Test
    @DisplayName("Deve retornar erro quando o cliente não tiver sido cadastrado no sistema")
    void deveRetornarErroQuandoClienteNaoCadastrado() {
        Integer clienteId = 1;

        doThrow(DadoNaoEncontradoException.class)
                .when(consultarClienteService).validarPorId(clienteId);

        assertThrows(DadoNaoEncontradoException.class, () -> servicoTestado.listar(clienteId));

        verify(consultarClienteService).validarPorId(clienteId);
        verify(pedidoRepository, never()).findAllByClienteId(clienteId);
    }
}