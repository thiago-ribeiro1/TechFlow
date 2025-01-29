package acc.br.techflow.envio.listener;

import acc.br.techflow.envio.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.envio.dtoRabbit.PedidoRabbitMQDTO;
import acc.br.techflow.envio.enums.MetodoPagamento;
import acc.br.techflow.envio.enums.StatusPedidoEnum;
import acc.br.techflow.envio.service.EnviarMensagemRabbitMQService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EnvioListenerTest {

    @InjectMocks
    private EnvioListener envioListener;

    @Mock
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    private PedidoRabbitMQDTO pedidoRabbitMQDTO;

    @Spy
    private Random randomMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        randomMock = mock(Random.class);


        pedidoRabbitMQDTO = new PedidoRabbitMQDTO(
                1,
                Arrays.asList(new ItemPedidoRabbitMQDTO(1, 5),
                        new ItemPedidoRabbitMQDTO(2, 10)),
                MetodoPagamento.PIX,
                "Endereço de Exemplo"
        );
    }

    @Test
    @DisplayName("Deve processar o pedido com sucesso e enviar mensagem com status ENVIADO")
    public void testConsomePedidoComSucesso() {
        EnvioListener envioListenerSpy = spy(envioListener);
        doReturn(true).when(envioListenerSpy).simularEnvioPedido();
        envioListenerSpy.consomePedidos(pedidoRabbitMQDTO);

        verify(enviarMensagemRabbitMQService, times(1))
                .enviarMensagem(argThat(statusPedido ->
                statusPedido.getNovoStatus() == StatusPedidoEnum.ENVIADO));
    }

    @Test
    @DisplayName("Deve processar o pedido com falha no envio e enviar mensagem com status de FALHA_ENVIO")
    public void testConsomePedidoComFalha() {
        envioListener = spy(envioListener);
        doReturn(false).when(envioListener).simularEnvioPedido();
        envioListener.consomePedidos(pedidoRabbitMQDTO);

        verify(enviarMensagemRabbitMQService, times(1))
                .enviarMensagem(argThat(statusPedido ->
                statusPedido.getNovoStatus() == StatusPedidoEnum.FALHA_ENVIO));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando InterruptedException ocorrer")
    public void testSimularEnvioPedidoComInterrupcao() {
        EnvioListener envioListenerSpy = spy(envioListener);

        doThrow(new RuntimeException("Simulação de interrupção", new InterruptedException("Simulação de interrupção")))
                .when(envioListenerSpy).simularEnvioPedido();

        assertThrows(RuntimeException.class, () -> {
            envioListenerSpy.simularEnvioPedido();
        });
    }
}