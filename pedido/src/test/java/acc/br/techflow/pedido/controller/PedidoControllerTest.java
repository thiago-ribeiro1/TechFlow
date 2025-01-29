package acc.br.techflow.pedido.controller;

import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.service.CadastrarPedidoService;
import acc.br.techflow.pedido.service.ConsultarPedidoService;
import acc.br.techflow.pedido.service.ListarTodosPedidosPorClienteService;
import acc.br.techflow.pedido.service.ListarTodosPedidosService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @InjectMocks
    private PedidoController controllerTestado;

    @Mock
    private CadastrarPedidoService cadastrarPedidoService;

    @Mock
    private ListarTodosPedidosService listarTodosPedidosService;

    @Mock
    private ListarTodosPedidosPorClienteService listarTodosPedidosPorClienteService ;

    @Mock
    private ConsultarPedidoService consultarPedidoService;

    @Test
    @DisplayName("Validar se controller de cadastrar pedido está chamando o service")
    void deveValidarSeControllerCadastrarPedidoChamaService() {
        CadastrarPedidoRequisicao requisicao = Instancio.of(CadastrarPedidoRequisicao.class).create();

        controllerTestado.cadastrar(requisicao);

        verify(cadastrarPedidoService).cadastrar(requisicao);
    }

    @Test
    @DisplayName("Validar se controller de listar todos os pedidos está chamando o service")
    void deveValidarSeControllerListarTodosPedidosChamaService() {
        controllerTestado.listarTodos();

        verify(listarTodosPedidosService).listar();
    }

    @Test
    @DisplayName("Validar se controller de listar todos os pedidos de um cliente específico está chamando o service")
    void deveValidarSeControllerListarTodosPedidosDeClienteChamaService() {
        Integer idCliente = 1;

        controllerTestado.listarTodosPorCliente(idCliente);

        verify(listarTodosPedidosPorClienteService).listar(idCliente);
    }

    @Test
    @DisplayName("Validar se controller de consultar pedido por ID está chamando o service")
    void deveValidarSeControllerConsultarPedidoChamaService() {
        Integer idPedido = 1;

        controllerTestado.consultar(idPedido);

        verify(consultarPedidoService).consultar(idPedido);
    }
}