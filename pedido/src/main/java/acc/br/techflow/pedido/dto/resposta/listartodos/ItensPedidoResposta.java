package acc.br.techflow.pedido.dto.resposta.listartodos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItensPedidoResposta {
    private Integer itemPedidoId;
    private Integer produtoId;
    private Integer quantidade;
}
