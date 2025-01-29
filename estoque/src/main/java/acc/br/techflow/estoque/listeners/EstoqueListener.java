package acc.br.techflow.estoque.listeners;

import acc.br.techflow.estoque.dominio.enums.StatusPedidoEnum;
import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.StatusPedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EnviarMensagemRabbitMQService;
import acc.br.techflow.estoque.service.EstoqueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstoqueListener {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @RabbitListener(queues = {"oito.fl.estoque.pedido-pago"})
    public void consomePedidos(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        estoqueService.atualizarEstoque(pedidoRabbitMQDTO.getItensPedido());
        atualizarStatusPedido(pedidoRabbitMQDTO);
    }

    //para produzir mensagens
    public void atualizarStatusPedido(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        StatusPedidoEnum novoStatus = StatusPedidoEnum.RETIRADA_APROVADA;

        StatusPedidoRabbitMQDTO statusPedidoDTO = new StatusPedidoRabbitMQDTO(
                pedidoRabbitMQDTO.getPedidoId(),
                LocalDateTime.now(),
                novoStatus
        );
        //produz mensagem para a exchange oito.ex.status-pedido
        enviarMensagemRabbitMQService.enviarMensagem(statusPedidoDTO);
        //produz mensagem para a exchange oito.ex.pedido
        enviarMensagemRabbitMQService.enviarMensagem("oito.retirada.aprovada", pedidoRabbitMQDTO);
    }
}

