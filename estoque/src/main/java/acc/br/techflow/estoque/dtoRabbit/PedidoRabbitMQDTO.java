package acc.br.techflow.estoque.dtoRabbit;

import acc.br.techflow.estoque.dominio.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoRabbitMQDTO {

    private Integer pedidoId;
    private List<ItemPedidoRabbitMQDTO> itensPedido;
    private MetodoPagamento metodoPagamento;
    private String endereco;

    public List<Integer> getProdutoIds() {
        return itensPedido.stream()
                .map(ItemPedidoRabbitMQDTO::getProdutoId)
                .toList();
    }

    public List<Integer> getQuantidades() {
        return itensPedido.stream()
                .map(ItemPedidoRabbitMQDTO::getQuantidadeSolicitada)
                .toList();
    }
}
