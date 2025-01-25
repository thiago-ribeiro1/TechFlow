package acc.br.techflow.envio.listener;

import acc.br.techflow.envio.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.envio.dtoRabbit.StatusPedidoRabbitMQDTO;
import acc.br.techflow.envio.enums.StatusPedidoEnum;
import acc.br.techflow.envio.service.EnviarMensagemRabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnvioListener {

    @Autowired
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @RabbitListener(queues = {"oito.fl.envio.retirada-aprovada"})
    public void consomePedidos(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        atualizarStatusPedido(pedidoRabbitMQDTO);
    }

    //para produzir mensagens
    private void atualizarStatusPedido(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        StatusPedidoEnum novoStatus = StatusPedidoEnum.ENVIADO;

        StatusPedidoRabbitMQDTO statusPedidoDTO = new StatusPedidoRabbitMQDTO(
                pedidoRabbitMQDTO.getPedidoId(),
                LocalDateTime.now(),
                novoStatus
        );
        //produz mensagem para a exchange oito.ex.status-pedido
       enviarMensagemRabbitMQService.enviarMensagem(statusPedidoDTO);
    }
}