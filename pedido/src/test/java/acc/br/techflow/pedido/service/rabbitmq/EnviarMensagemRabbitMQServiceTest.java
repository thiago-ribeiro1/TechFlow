package acc.br.techflow.pedido.service.rabbitmq;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.rabbitmq.PedidoRabbitMQDTO;
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
    @DisplayName("Deve enviar mensagem para o RabbitMQ com sucesso")
    void deveEnviarMensagemParaRabbitMQ() {
        String exchamge = "oito.ex.pedido";
        String routingKeyTeste = "teste";
        PedidoRabbitMQDTO pedidoRabbitMQDTO = Instancio.of(PedidoRabbitMQDTO.class).create();

        servicoTestado.enviarMensagem(routingKeyTeste, pedidoRabbitMQDTO);

        verify(rabbitTemplate).convertAndSend(exchamge, routingKeyTeste, pedidoRabbitMQDTO);
    }
}