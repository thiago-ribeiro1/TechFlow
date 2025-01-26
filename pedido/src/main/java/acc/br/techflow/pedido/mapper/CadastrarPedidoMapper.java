package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.openfeign.ItemPedidoOpenFeignDTO;
import acc.br.techflow.pedido.dto.rabbitmq.ItemPedidoRabbitMQDTO;
import acc.br.techflow.pedido.dto.rabbitmq.PedidoRabbitMQDTO;
import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.requisicao.ItemPedidoCadastrarPedidoRequisicao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CadastrarPedidoMapper {

    CadastrarPedidoMapper INSTANCIA = Mappers.getMapper(CadastrarPedidoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "metodoPagamento", source = "metodoPagamento")
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "cliente", ignore = true)
    Pedido converterDTORequisicaoParaEntidade(CadastrarPedidoRequisicao requisicao);

    @Mapping(target = "produtoId", source = "produtoId")
    @Mapping(target = "quantidadeProduto", source = "quantidade")
    List<ItemPedidoOpenFeignDTO> converterListaDTORequisicaoParaListaDTOOpenFeign(List<ItemPedidoCadastrarPedidoRequisicao> listaRequisicao);

    @Mapping(target = "pedidoId", source = "id")
    @Mapping(target = "itensPedido", ignore = true)
    @Mapping(target = "metodoPagamento", source = "metodoPagamento")
    @Mapping(target = "endereco", source = "endereco")
    PedidoRabbitMQDTO converterEntidadeParaDTORabbitMQ(Pedido entidade);

    @Mapping(target = "produtoId", source = "id")
    @Mapping(target = "quantidade", source = "quantidadeProduto")
    List<ItemPedidoRabbitMQDTO> converterListaEntidadeParaListaDTORabbitMQ(List<ItemPedido> listaEntidade);
}
