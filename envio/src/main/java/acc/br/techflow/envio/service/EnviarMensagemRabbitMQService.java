package acc.br.techflow.envio.service;

import acc.br.techflow.envio.dtoRabbit.StatusPedidoRabbitMQDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EnviarMensagemRabbitMQService {

    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO) {
        rabbitTemplate.convertAndSend("oito.ex.status-pedido", "", statusPedidoRabbitMQDTO);
    }
}