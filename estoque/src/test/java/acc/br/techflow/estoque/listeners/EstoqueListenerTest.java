package acc.br.techflow.estoque.listeners;

import acc.br.techflow.estoque.dominio.enums.MetodoPagamento;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EnviarMensagemRabbitMQService;
import acc.br.techflow.estoque.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

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
    @DisplayName("Deve verificar se os pedidos estão sendo consumidos corretamente")
    public void testConsomePedidos() {
        estoqueListener.consomePedidos(pedidoRabbitMQDTO);
        verify(estoqueService, times(1)).atualizarEstoque(anyList());
        verify(enviarMensagemRabbitMQService, times(1)).enviarMensagem(any());
        verify(estoqueService, times(1)).atualizarEstoque(anyList());
    }

    @Test
    @DisplayName("Deve falhar ao consumir pedidos e enviar mensagem")
    public void testConsomePedidosComFalhaNoEstoque() {
        doThrow(new RuntimeException("Erro ao atualizar estoque")).when(estoqueService).atualizarEstoque(anyList());

        assertThrows(RuntimeException.class, () -> estoqueListener.consomePedidos(pedidoRabbitMQDTO));

        verify(estoqueService, times(1)).atualizarEstoque(anyList());
        verify(enviarMensagemRabbitMQService, times(0)).enviarMensagem(any());
    }
}
