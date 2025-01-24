package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.StatusPedidoRabbitMQDTO;
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

    public void enviarMensagem(StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO) {
        rabbitTemplate.convertAndSend("oito.ex.status-pedido", "", statusPedidoRabbitMQDTO);
    }
}
