package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosPorClienteResposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ListarTodosPedidosPorClienteMapper {

    ListarTodosPedidosPorClienteMapper INSTANCIA = Mappers.getMapper(ListarTodosPedidosPorClienteMapper.class);

    @Mapping(target = "pedidoId", source = "id")
    @Mapping(target = "dataHora", source = "dataHora")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "metodoPagamento", source = "metodoPagamento")
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "clienteId", ignore = true)
    ListarTodosPedidosPorClienteResposta converterEntidadeParaDTOResposta(Pedido entidade);
}
