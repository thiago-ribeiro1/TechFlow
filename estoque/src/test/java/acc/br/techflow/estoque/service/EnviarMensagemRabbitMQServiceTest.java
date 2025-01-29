package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.StatusPedidoRabbitMQDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EnviarMensagemRabbitMQServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    private PedidoRabbitMQDTO pedidoRabbitMQDTO;
    private StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO;
    private String routingKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        pedidoRabbitMQDTO = new PedidoRabbitMQDTO();

        routingKey = "oito.ex.pedido.pago";
    }

    @Test
    @DisplayName("Deve com sucesso enviar mensagem para exchange de pedido usando routing key")
    public void testEnviarMensagemExchangePedido() {
        enviarMensagemRabbitMQService.enviarMensagem(routingKey, pedidoRabbitMQDTO);
        verify(rabbitTemplate, times(1)).convertAndSend("oito.ex.pedido", routingKey, pedidoRabbitMQDTO);
    }

    @Test
    @DisplayName("Deve falhar ao enviar mensagem para exchange de pedido usando routing key")
    public void testEnviarMensagemExchangePedidoComFalha() {
        doThrow(new RuntimeException("Falha no envio para RabbitMQ"))
                .when(rabbitTemplate).convertAndSend(eq("oito.ex.pedido"), eq(routingKey), eq(pedidoRabbitMQDTO));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            enviarMensagemRabbitMQService.enviarMensagem(routingKey, pedidoRabbitMQDTO);
        });

        assertEquals("Falha no envio para RabbitMQ", exception.getMessage());
        verify(rabbitTemplate, times(1)).convertAndSend("oito.ex.pedido", routingKey, pedidoRabbitMQDTO);
    }
    @Test
    @DisplayName("Deve com sucesso enviar mensagem para exchange de status-pedido")
    public void testEnviarMensagemExchangeStatusPedido() {
        enviarMensagemRabbitMQService.enviarMensagem(statusPedidoRabbitMQDTO);
        verify(rabbitTemplate, times(1)).convertAndSend("oito.ex.status-pedido","",statusPedidoRabbitMQDTO);
    }

    @Test
    @DisplayName("Deve falhar ao enviar mensagem para exchange de status-pedido")
    public void testEnviarMensagemExchangeStatusPedidoComFalha() {
        doThrow(new RuntimeException("Falha no envio para RabbitMQ"))
                .when(rabbitTemplate).convertAndSend(eq("oito.ex.status-pedido"), eq(""), eq(statusPedidoRabbitMQDTO));

        RuntimeException exception;
        exception = assertThrows(RuntimeException.class, () -> {
            enviarMensagemRabbitMQService.enviarMensagem(statusPedidoRabbitMQDTO);
        });

        assertEquals("Falha no envio para RabbitMQ", exception.getMessage());

        verify(rabbitTemplate, times(1)).convertAndSend("oito.ex.status-pedido", "", statusPedidoRabbitMQDTO);
    }
}