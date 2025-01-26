package acc.br.techflow.estoque.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ItemPedido {

    private Integer produtoId;
    private Integer quantidadeSolicitada;
}
