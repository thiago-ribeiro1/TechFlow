package acc.br.techflow.pagamento.service;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.dto.StatusPedidoRabbitMQDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProcessarPagamentoServiceTest {

    @InjectMocks
    private ProcessarPagamentoService servicoTestado;

    @Mock
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @Captor
    private ArgumentCaptor<StatusPedidoRabbitMQDTO> statusPedidoRabbitMQDTOCaptor;

    @Test
    @DisplayName("Deve validar se está enviando mensagem para o RabbitMQ com sucesso")
    public void deveValidarEnvioMensagemRabbitMQ() {
        PedidoRabbitMQDTO pedidoRabbitMQDTO = Instancio.of(PedidoRabbitMQDTO.class).create();

        servicoTestado.processar(pedidoRabbitMQDTO);

        verify(enviarMensagemRabbitMQService, times(1)).enviarMensagem(statusPedidoRabbitMQDTOCaptor.capture());
    }
}