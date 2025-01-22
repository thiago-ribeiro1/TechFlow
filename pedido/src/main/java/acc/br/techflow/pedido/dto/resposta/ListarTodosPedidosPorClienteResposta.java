package acc.br.techflow.pedido.dto.resposta;

import acc.br.techflow.pedido.dominio.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListarTodosPedidosPorClienteResposta {
    private Integer pedidoId;
    private LocalDateTime dataHora;
    private String observacao;
    private MetodoPagamento metodoPagamento;
    private String endereco;
    private Integer clienteId;
}
