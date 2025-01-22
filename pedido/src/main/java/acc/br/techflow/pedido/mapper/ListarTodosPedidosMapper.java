package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosResposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ListarTodosPedidosMapper {

    ListarTodosPedidosMapper INSTANCIA = Mappers.getMapper(ListarTodosPedidosMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dataHora", source = "dataHora")
    @Mapping(target = "observacao", source = "observacao")
    @Mapping(target = "metodoPagamento", source = "metodoPagamento")
    @Mapping(target = "endereco", source = "endereco")
    @Mapping(target = "clienteId", ignore = true)
    ListarTodosPedidosResposta converterEntidadeParaDTOResposta(Pedido entidade);
}
