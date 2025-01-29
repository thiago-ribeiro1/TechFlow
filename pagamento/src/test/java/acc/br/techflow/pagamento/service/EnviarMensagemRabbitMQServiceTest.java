package acc.br.techflow.pagamento.service;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.dto.StatusPedidoRabbitMQDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EnviarMensagemRabbitMQServiceTest {

    @InjectMocks
    private EnviarMensagemRabbitMQService servicoTestado;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    @DisplayName("Deve enviar mensagem para a exchange de pedido do RabbitMQ com sucesso")
    public void deveEnviarMensagemParaExchangePedido() {
        String exchamge = "oito.ex.pedido";
        String routingKeyTeste = "teste";
        PedidoRabbitMQDTO pedidoRabbitMQDTO = Instancio.of(PedidoRabbitMQDTO.class).create();

        servicoTestado.enviarMensagem(routingKeyTeste, pedidoRabbitMQDTO);

        verify(rabbitTemplate).convertAndSend(exchamge, routingKeyTeste, pedidoRabbitMQDTO);
    }

    @Test
    @DisplayName("Deve enviar mensagem para a exchange de status do pedido do RabbitMQ com sucesso")
    public void deveEnviarMensagemParaExchangeStatusPedido() {
        String exchamge = "oito.ex.status-pedido";
        String routingKeyTeste = "";
        StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO = Instancio.of(StatusPedidoRabbitMQDTO.class).create();

        servicoTestado.enviarMensagem(statusPedidoRabbitMQDTO);

        verify(rabbitTemplate).convertAndSend(exchamge, routingKeyTeste, statusPedidoRabbitMQDTO);
    }
}