package acc.br.techflow.pedido.dto.resposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoConsultarPedidoResposta {

    private Integer produtoId;
    private BigDecimal valorProduto;
    private Integer quantidadeProduto;
    private BigDecimal valorTotalItem;
}
