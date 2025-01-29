package acc.br.techflow.pedido.listener;

import acc.br.techflow.pedido.dto.rabbitmq.StatusPedidoRabbitMQDTO;
import acc.br.techflow.pedido.service.CadastrarNovoStatusPedidoService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NovoStatusListenerTest {

    @InjectMocks
    private NovoStatusListener listenerTestado;

    @Mock
    private CadastrarNovoStatusPedidoService cadastrarNovoStatusPedidoService;

    @Test
    @DisplayName("Validar se listener está chamando o service de cadastrar novo status do pedido")
    void deveValidarSeListenerChamaService() {
        StatusPedidoRabbitMQDTO dtoRabbitMQ = Instancio.of(StatusPedidoRabbitMQDTO.class).create();

        listenerTestado.novoStatus(dtoRabbitMQ);

        verify(cadastrarNovoStatusPedidoService).cadastrar(dtoRabbitMQ);
    }
}