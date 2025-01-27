package acc.br.techflow.pedido.listener;

import acc.br.techflow.pedido.dto.rabbitmq.StatusPedidoRabbitMQDTO;
import acc.br.techflow.pedido.service.CadastrarNovoStatusPedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NovoStatusListener {

    @Autowired
    private CadastrarNovoStatusPedidoService cadastrarNovoStatusPedidoService;

    @RabbitListener(queues = {"oito.fl.pedido.novo-status"})
    public void novoStatus(StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO) {
        cadastrarNovoStatusPedidoService.cadastrar(statusPedidoRabbitMQDTO);
    }
}
