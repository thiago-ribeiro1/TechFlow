package acc.br.techflow.pedido.dto.openfeign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoOpenFeignDTO {

    private Integer produtoId;
    private Integer quantidadeProduto;
}
