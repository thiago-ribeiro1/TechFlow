package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dto.rabbitmq.ItemPedidoRabbitMQDTO;

import java.util.List;

public class ItemPedidoRabbitMQMapper {

    public static List<ItemPedidoRabbitMQDTO> converterListaEntidadeParaListaDTORabbitMQ(List<ItemPedido> listaEntidade) {
        return listaEntidade.stream().map(itemPedido -> {
            ItemPedidoRabbitMQDTO itemPedidoRabbitMQDTO = new ItemPedidoRabbitMQDTO();

            itemPedidoRabbitMQDTO.setProdutoId(itemPedido.getProduto().getId());
            itemPedidoRabbitMQDTO.setQuantidade(itemPedido.getQuantidadeProduto());

            return itemPedidoRabbitMQDTO;
        }).toList();
    }
}
