package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.*;
import acc.br.techflow.pedido.dto.openfeign.ItemPedidoOpenFeignDTO;
import acc.br.techflow.pedido.dto.rabbitmq.PedidoRabbitMQDTO;
import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.requisicao.ItemPedidoCadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.resposta.CadastrarPedidoResposta;
import acc.br.techflow.pedido.exception.DadoRepetidoException;
import acc.br.techflow.pedido.openfeign.EstoqueOpenFeign;
import acc.br.techflow.pedido.repository.ItemPedidoRepository;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import acc.br.techflow.pedido.service.consultar.ConsultarClienteService;
import acc.br.techflow.pedido.service.consultar.ConsultarProdutoService;
import acc.br.techflow.pedido.service.rabbitmq.EnviarMensagemRabbitMQService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarPedidoServiceTest {

    @InjectMocks
    private CadastrarPedidoService servicoTestado;

    @Mock
    private ConsultarClienteService consultarClienteService;

    @Mock
    private ConsultarProdutoService consultarProdutoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private StatusPedidoRepository statusPedidoRepository;

    @Mock
    private EstoqueOpenFeign estoqueOpenFeign;

    @Mock
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @Captor
    private ArgumentCaptor<Pedido> pedidoCaptor;

    @Captor
    private ArgumentCaptor<List<ItemPedido>> itensPedidoCaptor;

    @Captor
    private ArgumentCaptor<StatusPedido> statusPedidoCaptor;

    @Captor
    private ArgumentCaptor<List<ItemPedidoOpenFeignDTO>> itensPedidoFormatadoParaOpenFeignCaptor;

    @Captor
    private ArgumentCaptor<String> routingKeyCaptor;

    @Captor
    private ArgumentCaptor<PedidoRabbitMQDTO> pedidoRabbitMQDTOCaptor;

    @Test
    @DisplayName("Deve cadastrar um novo pedido e enviar dados do pedido para o RabbitMQ quando o pedido não tiver produtos repetidos, tiver id de cliente e id de produtos válidos e tiver estoque")
    void deveCadastrarUmNovoPedidoEEnviarDadosParaORabbitMQ() {
        CadastrarPedidoRequisicao requisicao = Instancio.of(CadastrarPedidoRequisicao.class).create();
        Cliente cliente = Instancio.of(Cliente.class).create();
        Produto produto = Instancio.of(Produto.class).create();

        when(consultarClienteService.consultarPorId(requisicao.getClienteId()))
                .thenReturn(cliente);
        requisicao.getItensPedido().forEach(itemPedidoRequisicao -> {
            when(consultarProdutoService.consultarPorId(itemPedidoRequisicao.getProdutoId()))
                    .thenReturn(produto);
        });
        when(estoqueOpenFeign.validarEstoque(itensPedidoFormatadoParaOpenFeignCaptor.capture())).thenReturn(true);

        CadastrarPedidoResposta resposta = servicoTestado.cadastrar(requisicao);

        verify(consultarClienteService).consultarPorId(requisicao.getClienteId());
        verify(pedidoRepository).save(pedidoCaptor.capture());
        verify(itemPedidoRepository).saveAll(itensPedidoCaptor.capture());
        verify(statusPedidoRepository, times(1)).save(statusPedidoCaptor.capture());
        verify(estoqueOpenFeign).validarEstoque(itensPedidoFormatadoParaOpenFeignCaptor.capture());
        verify(enviarMensagemRabbitMQService).enviarMensagem(routingKeyCaptor.capture(), pedidoRabbitMQDTOCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();

        assertEquals(pedidoSalvo.getId(), resposta.getPedidoId());
        assertEquals("Pedido efetuado com sucesso!", resposta.getMensagem());
    }

    @Test
    @DisplayName("Deve cadastrar um novo pedido, mas não enviar os dados do pedido para o RabbitMQ quando o pedido não tiver estoque, mas tiver id de cliente válido, ids de produtos válidos e não tiver produtos repetidos")
    void deveCadastrarUmNovoPedidoMasNaoEnviarDadosParaORabbitMQ() {
        CadastrarPedidoRequisicao requisicao = Instancio.of(CadastrarPedidoRequisicao.class).create();
        Cliente cliente = Instancio.of(Cliente.class).create();
        Produto produto = Instancio.of(Produto.class).create();

        when(consultarClienteService.consultarPorId(requisicao.getClienteId()))
                .thenReturn(cliente);
        requisicao.getItensPedido().forEach(itemPedidoRequisicao -> {
            when(consultarProdutoService.consultarPorId(itemPedidoRequisicao.getProdutoId()))
                    .thenReturn(produto);
        });
        when(estoqueOpenFeign.validarEstoque(itensPedidoFormatadoParaOpenFeignCaptor.capture())).thenReturn(false);

        CadastrarPedidoResposta resposta = servicoTestado.cadastrar(requisicao);

        verify(consultarClienteService).consultarPorId(requisicao.getClienteId());
        verify(pedidoRepository).save(pedidoCaptor.capture());
        verify(itemPedidoRepository).saveAll(itensPedidoCaptor.capture());
        verify(statusPedidoRepository, times(2)).save(statusPedidoCaptor.capture());
        verify(estoqueOpenFeign).validarEstoque(itensPedidoFormatadoParaOpenFeignCaptor.capture());
        verify(enviarMensagemRabbitMQService, never()).enviarMensagem(routingKeyCaptor.capture(), pedidoRabbitMQDTOCaptor.capture());

        Pedido pedidoSalvo = pedidoCaptor.getValue();

        assertEquals(pedidoSalvo.getId(), resposta.getPedidoId());
        assertEquals("Pedido efetuado com sucesso!", resposta.getMensagem());
    }

    @Test
    @DisplayName("Não deve cadastrar um novo pedido quando tiver id de produtos repetidos")
    void naoDeveCadastrarUmNovoPedidoQuandoIdsDeProdutosRepetidos() {
        CadastrarPedidoRequisicao requisicao = Instancio.of(CadastrarPedidoRequisicao.class).create();
        Integer idFixo = 1;
        List<ItemPedidoCadastrarPedidoRequisicao> itensPedidoRequisicao =
                Instancio.ofList(ItemPedidoCadastrarPedidoRequisicao.class)
                        .size(2).create();
        itensPedidoRequisicao.get(0).setProdutoId(idFixo);
        itensPedidoRequisicao.get(1).setProdutoId(idFixo);
        requisicao.setItensPedido(itensPedidoRequisicao);

        DadoRepetidoException exception =
                assertThrows(DadoRepetidoException.class, () -> servicoTestado.cadastrar(requisicao));

        verify(consultarClienteService, never()).consultarPorId(requisicao.getClienteId());
        verify(pedidoRepository, never()).save(pedidoCaptor.capture());
        verify(itemPedidoRepository, never()).saveAll(itensPedidoCaptor.capture());
        verify(statusPedidoRepository, never()).save(statusPedidoCaptor.capture());
        verify(estoqueOpenFeign, never()).validarEstoque(itensPedidoFormatadoParaOpenFeignCaptor.capture());
        verify(enviarMensagemRabbitMQService, never()).enviarMensagem(routingKeyCaptor.capture(), pedidoRabbitMQDTOCaptor.capture());

        assertEquals("Não pode haver produtos repetidos no pedido", exception.getMessage());
    }
}