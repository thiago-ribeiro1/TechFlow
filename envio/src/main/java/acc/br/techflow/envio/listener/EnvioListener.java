package acc.br.techflow.envio.listener;

import acc.br.techflow.envio.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.envio.dtoRabbit.StatusPedidoRabbitMQDTO;
import acc.br.techflow.envio.enums.StatusPedidoEnum;
import acc.br.techflow.envio.service.EnviarMensagemRabbitMQService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EnvioListener {

    @Autowired
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @RabbitListener(queues = {"oito.fl.envio.retirada-aprovada"})
    public void consomePedidos(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        if (simularEnvioPedido()) {
            atualizarStatusPedido(pedidoRabbitMQDTO, StatusPedidoEnum.ENVIADO);  // Status ENVIADO se o envio foi bem-sucedido
        } else {
            atualizarStatusPedido(pedidoRabbitMQDTO, StatusPedidoEnum.FALHA_ENVIO);  // Status FALHA_ENVIO se o envio falhou
        }
    }

    //para produzir mensagens
    private void atualizarStatusPedido(PedidoRabbitMQDTO pedidoRabbitMQDTO, StatusPedidoEnum novoStatus) {
        StatusPedidoRabbitMQDTO statusPedidoDTO = new StatusPedidoRabbitMQDTO(
                pedidoRabbitMQDTO.getPedidoId(),
                LocalDateTime.now(),
                novoStatus
        );
        //produz mensagem para a exchange oito.ex.status-pedido
        enviarMensagemRabbitMQService.enviarMensagem(statusPedidoDTO);
    }

    private boolean simularEnvioPedido() {
        try {
            // Simulação do tempo de processamento de envio
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int numeroAleatorio = random.nextInt(10);

        return numeroAleatorio < 7;
    }
}