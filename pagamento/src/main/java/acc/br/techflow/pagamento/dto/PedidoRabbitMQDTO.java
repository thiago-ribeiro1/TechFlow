package acc.br.techflow.pagamento.dto;

import acc.br.techflow.pagamento.enums.MetodoPagamento;
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
}
