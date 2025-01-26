package acc.br.techflow.pagamento.listener;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.service.ProcessarPagamentoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NovoPedidoListener {

    @Autowired
    private ProcessarPagamentoService processarPagamentoService;

    @RabbitListener(queues = {"oito.fl.pagamento.novo-pedido"})
    public void novoPedido(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        processarPagamentoService.processar(pedidoRabbitMQDTO);
    }
}
