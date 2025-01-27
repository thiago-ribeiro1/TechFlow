package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dominio.StatusPedido;
import acc.br.techflow.pedido.dto.rabbitmq.StatusPedidoRabbitMQDTO;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarNovoStatusPedidoServiceTest {

    @InjectMocks
    private CadastrarNovoStatusPedidoService servicoTestado;

    @Mock
    private StatusPedidoRepository statusPedidoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Captor
    private ArgumentCaptor<StatusPedido> statusPedidoCaptor;

    @Test
    @DisplayName("Deve salvar um novo status para o pedido quando o ID do pedido corresponder ao ID de um pedido já cadastrado no sistema")
    void deveSalvarNovoStatusParaUmPedido() {
        StatusPedidoRabbitMQDTO dtoRabbitMQ = Instancio.of(StatusPedidoRabbitMQDTO.class).create();
        Pedido pedido = Instancio.of(Pedido.class).create();

        when(pedidoRepository.findById(dtoRabbitMQ.getPedidoId()))
                .thenReturn(Optional.ofNullable(pedido));

        servicoTestado.cadastrar(dtoRabbitMQ);

        verify(pedidoRepository).findById(dtoRabbitMQ.getPedidoId());
        verify(statusPedidoRepository).save(statusPedidoCaptor.capture());
    }

    @Test
    @DisplayName("Deve retornar erro quando o ID do pedido não corresponder ao ID de um pedido já cadastrado no sistema")
    void deveRetornarErroQuandoIdPedidoInvalido() {
        StatusPedidoRabbitMQDTO dtoRabbitMQ = Instancio.of(StatusPedidoRabbitMQDTO.class).create();

        DadoNaoEncontradoException exception =
                assertThrows(DadoNaoEncontradoException.class, () -> servicoTestado.cadastrar(dtoRabbitMQ));

        verify(pedidoRepository).findById(dtoRabbitMQ.getPedidoId());
        verify(statusPedidoRepository, never()).save(statusPedidoCaptor.capture());

        assertEquals("Pedido de ID " + dtoRabbitMQ.getPedidoId() + " não encontrado", exception.getMessage());
    }
}