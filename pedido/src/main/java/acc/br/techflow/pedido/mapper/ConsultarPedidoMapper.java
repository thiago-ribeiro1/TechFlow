package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dominio.StatusPedido;
import acc.br.techflow.pedido.dto.resposta.ConsultarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.StatusPedidoConsultarPedidoResposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConsultarPedidoMapper {

    ConsultarPedidoMapper INSTANCIA = Mappers.getMapper(ConsultarPedidoMapper.class);

    @Mapping(target = "pedidoId", source = "id")
    @Mapping(target = "itensPedido", ignore = true)
    @Mapping(target = "valorTotalPedido", ignore = true)
    @Mapping(target = "dataHora", source = "dataHora")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "metodoPagamento", source = "metodoPagamento")
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "statusPedido", ignore = true)
    ConsultarPedidoResposta converterEntidadeParaDTOResposta(Pedido entidade);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "dataHora", source = "dataHora")
    List<StatusPedidoConsultarPedidoResposta> converterListaEntidadeParaListaDTOResposta(List<StatusPedido> entidade);
}
