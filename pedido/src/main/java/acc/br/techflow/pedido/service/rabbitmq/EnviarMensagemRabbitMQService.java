package acc.br.techflow.pedido.service.rabbitmq;

import acc.br.techflow.pedido.dto.rabbitmq.PedidoRabbitMQDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EnviarMensagemRabbitMQService {

    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(String routingKey, PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        rabbitTemplate.convertAndSend("oito.ex.pedido", routingKey, pedidoRabbitMQDTO);
    }
}
