package acc.br.techflow.pedido.dto.resposta.listartodos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListarTodosPedidosResposta {
    private Integer id;
    private List<ItensPedidoResposta> itensPedido;
    private Integer clienteId;
}
