package acc.br.techflow.pagamento.listener;

import acc.br.techflow.pagamento.dto.PedidoRabbitMQDTO;
import acc.br.techflow.pagamento.service.ProcessarPagamentoService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NovoPedidoListenerTest {

    @InjectMocks
    private NovoPedidoListener listenerTestado;

    @Mock
    private ProcessarPagamentoService processarPagamentoService;

    @Test
    @DisplayName("Validar se listener está chamando o service de processar pagamento")
    void deveValidarSeListenerChamaService() {
        PedidoRabbitMQDTO dtoRabbitMQ = Instancio.of(PedidoRabbitMQDTO.class).create();

        listenerTestado.novoPedido(dtoRabbitMQ);

        verify(processarPagamentoService).processar(dtoRabbitMQ);
    }
}