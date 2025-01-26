package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.*;
import acc.br.techflow.pedido.dominio.enums.StatusPedidoEnum;
import acc.br.techflow.pedido.dto.openfeign.ItemPedidoOpenFeignDTO;
import acc.br.techflow.pedido.dto.rabbitmq.ItemPedidoRabbitMQDTO;
import acc.br.techflow.pedido.dto.rabbitmq.PedidoRabbitMQDTO;
import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.requisicao.ItemPedidoCadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.resposta.CadastrarPedidoResposta;
import acc.br.techflow.pedido.exception.DadoRepetidoException;
import acc.br.techflow.pedido.mapper.CadastrarPedidoMapper;
import acc.br.techflow.pedido.openfeign.EstoqueOpenFeign;
import acc.br.techflow.pedido.repository.ItemPedidoRepository;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import acc.br.techflow.pedido.service.consultar.ConsultarClienteService;
import acc.br.techflow.pedido.service.consultar.ConsultarProdutoService;
import acc.br.techflow.pedido.service.rabbitmq.EnviarMensagemRabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class CadastrarPedidoService {

    @Autowired
    private ConsultarClienteService consultarClienteService;

    @Autowired
    private ConsultarProdutoService consultarProdutoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    @Autowired
    private EstoqueOpenFeign estoqueOpenFeign;

    @Autowired
    private EnviarMensagemRabbitMQService enviarMensagemRabbitMQService;

    @Transactional
    public CadastrarPedidoResposta cadastrar(CadastrarPedidoRequisicao requisicao) {
        validarSeTemProdutoRepetido(requisicao.getItensPedido());

        Cliente clientePedido = consultarClienteService.consultarPorId(requisicao.getClienteId());

        Pedido pedido = CadastrarPedidoMapper.INSTANCIA.converterDTORequisicaoParaEntidade(requisicao);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setCliente(clientePedido);
        pedidoRepository.save(pedido);

        List<ItemPedido> itensPedido = retornarListaItemPedidoEntidade(requisicao.getItensPedido(), pedido);
        itemPedidoRepository.saveAll(itensPedido);

        StatusPedido statusPedido = new StatusPedido(StatusPedidoEnum.EM_ANDAMENTO, LocalDateTime.now(), pedido);
        statusPedidoRepository.save(statusPedido);

        List<ItemPedidoOpenFeignDTO> itensPedidoFormatadoParaOpenFeign = CadastrarPedidoMapper.INSTANCIA.converterListaDTORequisicaoParaListaDTOOpenFeign(requisicao.getItensPedido());
        Boolean temEstoque = estoqueOpenFeign.validarEstoque(itensPedidoFormatadoParaOpenFeign);

        CadastrarPedidoResposta resposta = new CadastrarPedidoResposta(pedido.getId(), "Pedido efetuado com sucesso!");

        if(!temEstoque) {
            StatusPedido statusSemEstoque = new StatusPedido(StatusPedidoEnum.SEM_ESTOQUE, LocalDateTime.now(), pedido);
            statusPedidoRepository.save(statusSemEstoque);

            return resposta;
        }

        PedidoRabbitMQDTO pedidoFormatadoParaRabbitMQ = CadastrarPedidoMapper.INSTANCIA.converterEntidadeParaDTORabbitMQ(pedido);
        List<ItemPedidoRabbitMQDTO> itensPedidoFormatadoParaRabbitMQ = CadastrarPedidoMapper.INSTANCIA.converterListaEntidadeParaListaDTORabbitMQ(itensPedido);
        pedidoFormatadoParaRabbitMQ.setItensPedido(itensPedidoFormatadoParaRabbitMQ);

        enviarMensagemRabbitMQService.enviarMensagem("oito.novo.pedido", pedidoFormatadoParaRabbitMQ);

        return resposta;
    }

    private void validarSeTemProdutoRepetido(List<ItemPedidoCadastrarPedidoRequisicao> itensPedidoRequisicao) {
        List<Integer> listaIdsProdutosPedido = itensPedidoRequisicao.stream().map(ItemPedidoCadastrarPedidoRequisicao::getProdutoId).toList();
        validarSeTemIdRepetido(listaIdsProdutosPedido);
    }

    private void validarSeTemIdRepetido(List<Integer> listaIds) {
        HashSet<Integer> set = new HashSet<>();

        for (int id : listaIds) {
            if (!set.add(id)) {
                // Se não conseguiu adicionar ao HashSet, é porque é repetido
                throw new DadoRepetidoException("Não pode haver produtos repetidos no pedido");
            }
        }
    }

    private List<ItemPedido> retornarListaItemPedidoEntidade(List<ItemPedidoCadastrarPedidoRequisicao> itensPedidoRequisicao, Pedido pedido) {
        return itensPedidoRequisicao.stream().map(itemPedidoRequisicao -> {
            ItemPedido itemPedidoEntidade = new ItemPedido();

            Produto produto = consultarProdutoService.consultarPorId(itemPedidoRequisicao.getProdutoId());

            itemPedidoEntidade.setProduto(produto);
            itemPedidoEntidade.setQuantidadeProduto(itemPedidoRequisicao.getQuantidade());
            itemPedidoEntidade.setPedido(pedido);

            return itemPedidoEntidade;
        }).toList();
    }
}
