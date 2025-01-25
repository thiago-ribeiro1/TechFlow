package acc.br.techflow.pedido.listener;

import acc.br.techflow.pedido.dto.rabbitmq.StatusPedidoRabbitMQDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NovoStatusListener {

    @RabbitListener(queues = {"oito.fl.pedido.novo-status"})
    public void novoStatus(StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO) {
        System.out.println("Pedido ID: " + statusPedidoRabbitMQDTO.getPedidoId());
        System.out.println("Novo Status: " + statusPedidoRabbitMQDTO.getNovoStatus());
        System.out.println("Data e hora: " + statusPedidoRabbitMQDTO.getDataHora());
    }
}
