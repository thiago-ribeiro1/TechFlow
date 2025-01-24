package acc.br.techflow.estoque.listeners;

import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EstoqueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstoqueListener {

    @Autowired
    private EstoqueService estoqueService;

    @RabbitListener(queues = {"oito.fl.estoque.pedido-pago"})
    public void consomePedidos(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        List<Integer> produtoIds = pedidoRabbitMQDTO.getProdutoIds();
        List<Integer> quantidades = pedidoRabbitMQDTO.getQuantidades();

        estoqueService.processarPedidoEstoque(produtoIds, quantidades);
    }
}

