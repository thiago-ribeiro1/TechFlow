package acc.br.techflow.envio.dtoRabbit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoRabbitMQDTO {

    private Integer produtoId;
    private Integer quantidade;
}
