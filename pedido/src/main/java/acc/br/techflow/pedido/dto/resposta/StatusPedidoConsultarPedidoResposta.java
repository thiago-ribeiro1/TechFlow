package acc.br.techflow.pedido.dto.resposta;

import acc.br.techflow.pedido.dominio.enums.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusPedidoConsultarPedidoResposta {

    private StatusPedidoEnum status;
    private LocalDateTime dataHora;
}
