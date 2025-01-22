package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dto.resposta.ItemPedidoConsultarPedidoResposta;

import java.math.BigDecimal;
import java.util.List;

public class ItemPedidoConsultarPedidoMapper {

    public static List<ItemPedidoConsultarPedidoResposta> converterListaEntidadeParaListaDTOResposta(List<ItemPedido> itensPedido) {
        return itensPedido.stream().map(itemPedido -> {
            ItemPedidoConsultarPedidoResposta itemPedidoResposta = new ItemPedidoConsultarPedidoResposta();

            itemPedidoResposta.setProdutoId(itemPedido.getProduto().getId());
            itemPedidoResposta.setValorProduto(itemPedido.getProduto().getValor());
            itemPedidoResposta.setQuantidadeProduto(itemPedido.getQuantidadeProduto());

            BigDecimal valorTotalItemPedido = itemPedido.getProduto().getValor().multiply(BigDecimal.valueOf(itemPedido.getQuantidadeProduto()));
            itemPedidoResposta.setValorTotalItem(valorTotalItemPedido);

            return itemPedidoResposta;
        }).toList();
    }
}
