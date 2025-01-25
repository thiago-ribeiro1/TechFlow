package acc.br.techflow.pedido.dto.resposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CadastrarPedidoResposta {

    private Integer pedidoId;
    private String mensagem;
}
