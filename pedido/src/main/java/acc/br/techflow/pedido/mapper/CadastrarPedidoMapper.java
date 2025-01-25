package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
}
