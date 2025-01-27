package acc.br.techflow.estoque.listeners;

import acc.br.techflow.estoque.dominio.enums.MetodoPagamento;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EnviarMensagemRabbitMQService;
import acc.br.techflow.estoque.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EstoqueListenerTest {

    @InjectMocks
    private EstoqueListener estoqueListener;

    @Mock
    private EstoqueService estoqueService;

    @Mock
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    private PedidoRabbitMQDTO pedidoRabbitMQDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoRabbitMQDTO = new PedidoRabbitMQDTO(
                1,
                Arrays.asList(new ItemPedidoRabbitMQDTO(1, 5),
                        new ItemPedidoRabbitMQDTO(2, 10)),
                MetodoPagamento.PIX,
                "Endereço de Exemplo"
        );
    }

    @Test
    public void testConsomePedidos() {
        estoqueListener.consomePedidos(pedidoRabbitMQDTO);
        verify(estoqueService, times(1)).processarPedidoEstoque(anyList(), anyList());
        verify(enviarMensagemRabbitMQService, times(1)).enviarMensagem(any());
        verify(estoqueService, times(1)).processarPedidoEstoque(any(), any());
    }
}
