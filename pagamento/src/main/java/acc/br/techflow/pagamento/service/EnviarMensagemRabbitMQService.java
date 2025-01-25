package acc.br.techflow.pagamento.service;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.dto.StatusPedidoRabbitMQDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnviarMensagemRabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarMensagem(String routingKey, PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        rabbitTemplate.convertAndSend("oito.ex.pedido", routingKey, pedidoRabbitMQDTO);
    }


    public void enviarMensagem(StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO) {
        rabbitTemplate.convertAndSend("oito.ex.status-pedido", "", statusPedidoRabbitMQDTO);
    }
}
