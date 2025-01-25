package acc.br.techflow.estoque.dtoRabbit;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ItemPedidoRabbitMQDTO {

    private Integer produtoId;
    private Integer quantidade;
}
