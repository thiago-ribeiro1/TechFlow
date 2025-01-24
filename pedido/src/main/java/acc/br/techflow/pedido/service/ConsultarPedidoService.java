package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dominio.StatusPedido;
import acc.br.techflow.pedido.dto.resposta.ConsultarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.ItemPedidoConsultarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.StatusPedidoConsultarPedidoResposta;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.mapper.ConsultarPedidoMapper;
import acc.br.techflow.pedido.mapper.ItemPedidoConsultarPedidoMapper;
import acc.br.techflow.pedido.repository.ItemPedidoRepository;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsultarPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    public ConsultarPedidoResposta consultar(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new DadoNaoEncontradoException("Pedido não encontrado"));
        List<ItemPedido> itensPedido = itemPedidoRepository.findAllByPedidoId(pedidoId);
        List<StatusPedido> statusPedido = statusPedidoRepository.findAllByPedidoId(pedidoId);

        List<ItemPedidoConsultarPedidoResposta> itensPedidoResposta = ItemPedidoConsultarPedidoMapper.converterListaEntidadeParaListaDTOResposta(itensPedido);
        List<StatusPedidoConsultarPedidoResposta> statusPedidoResposta = ConsultarPedidoMapper.INSTANCIA.converterListaEntidadeParaListaDTOResposta(statusPedido);

        ConsultarPedidoResposta pedidoResposta = ConsultarPedidoMapper.INSTANCIA.converterEntidadeParaDTOResposta(pedido);
        pedidoResposta.setClienteId(pedido.getCliente().getId());
        pedidoResposta.setItensPedido(itensPedidoResposta);
        pedidoResposta.setStatusPedido(statusPedidoResposta);
        pedidoResposta.setValorTotalPedido(calcularValorTotalPedido(itensPedidoResposta));

        return pedidoResposta;
    }

    private BigDecimal calcularValorTotalPedido(List<ItemPedidoConsultarPedidoResposta> itensPedido) {
        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        for(ItemPedidoConsultarPedidoResposta itemPedido : itensPedido) {
            valorTotalPedido = valorTotalPedido.add(itemPedido.getValorTotalItem());
        }

        return valorTotalPedido;
    }
}
