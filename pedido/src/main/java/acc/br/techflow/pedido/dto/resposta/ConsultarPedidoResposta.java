package acc.br.techflow.pedido.dto.resposta;

import acc.br.techflow.pedido.dominio.enums.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConsultarPedidoResposta {

    private Integer pedidoId;
    private List<ItemPedidoConsultarPedidoResposta> itensPedido;
    private BigDecimal valorTotalPedido;
    private LocalDateTime dataHora;
    private String observacao;
    private MetodoPagamento metodoPagamento;
    private String endereco;
    private Integer clienteId;
    private List<StatusPedidoConsultarPedidoResposta> statusPedido;
}
