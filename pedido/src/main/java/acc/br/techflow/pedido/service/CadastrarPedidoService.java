package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.*;
import acc.br.techflow.pedido.dominio.enums.StatusPedidoEnum;
import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.requisicao.ItemPedidoCadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.resposta.CadastrarPedidoResposta;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.exception.DadoRepetidoException;
import acc.br.techflow.pedido.mapper.CadastrarPedidoMapper;
import acc.br.techflow.pedido.repository.ItemPedidoRepository;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.ProdutoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import acc.br.techflow.pedido.service.consultar.ConsultarClienteService;
import acc.br.techflow.pedido.service.consultar.ConsultarProdutoService;
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

    @Transactional
    public CadastrarPedidoResposta cadastrar(CadastrarPedidoRequisicao requisicao) {
        List<Integer> listaIdsProdutosPedido = retornarListaComIdsDosProdutos(requisicao.getItensPedido());
        validarSeTemIdRepetido(listaIdsProdutosPedido);

        Cliente clientePedido = consultarClienteService.consultarPorId(requisicao.getClienteId());

        Pedido pedido = CadastrarPedidoMapper.INSTANCIA.converterDTORequisicaoParaEntidade(requisicao);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setCliente(clientePedido);
        pedidoRepository.save(pedido);

        List<ItemPedido> itensPedido = retornarListaItemPedidoEntidade(requisicao.getItensPedido(), pedido);
        itemPedidoRepository.saveAll(itensPedido);

        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setStatus(StatusPedidoEnum.EM_ANDAMENTO);
        statusPedido.setDataHora(LocalDateTime.now());
        statusPedido.setPedido(pedido);
        statusPedidoRepository.save(statusPedido);


        return null;
    }

    private List<Integer> retornarListaComIdsDosProdutos(List<ItemPedidoCadastrarPedidoRequisicao> itensPedidoRequisicao) {
        return itensPedidoRequisicao.stream().map(ItemPedidoCadastrarPedidoRequisicao::getProdutoId).toList();
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
