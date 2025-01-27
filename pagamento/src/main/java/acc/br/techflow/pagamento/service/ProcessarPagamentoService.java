package acc.br.techflow.pagamento.service;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.dto.StatusPedidoRabbitMQDTO;
import acc.br.techflow.pagamento.enums.StatusPedidoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ProcessarPagamentoService {

    @Autowired
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    public void processar(PedidoRabbitMQDTO pedidoRabbitMQDTO) {
        StatusPedidoRabbitMQDTO statusPedidoRabbitMQDTO = new StatusPedidoRabbitMQDTO();
        statusPedidoRabbitMQDTO.setPedidoId(pedidoRabbitMQDTO.getPedidoId());

        if(!pagamentoRealizado()) {
            statusPedidoRabbitMQDTO.setNovoStatus(StatusPedidoEnum.FALHA_PAGAMENTO);
            statusPedidoRabbitMQDTO.setDataHora(LocalDateTime.now());
            enviarMensagemRabbitMQService.enviarMensagem(statusPedidoRabbitMQDTO);
            return;
        }

        statusPedidoRabbitMQDTO.setNovoStatus(StatusPedidoEnum.PAGAMENTO_REALIZADO);
        statusPedidoRabbitMQDTO.setDataHora(LocalDateTime.now());

        enviarMensagemRabbitMQService.enviarMensagem(statusPedidoRabbitMQDTO);
        enviarMensagemRabbitMQService.enviarMensagem("oito.pedido.pago", pedidoRabbitMQDTO);
    }

    public boolean pagamentoRealizado() {
        try {
            // Simulação do tempo de processamento de pagamento
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Random random = new Random();
        int numeroAleatorio = random.nextInt(10);

        return numeroAleatorio <= 7;
    }
}
