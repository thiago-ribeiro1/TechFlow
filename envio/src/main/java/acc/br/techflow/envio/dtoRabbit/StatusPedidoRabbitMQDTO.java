package acc.br.techflow.envio.dtoRabbit;

import acc.br.techflow.envio.enums.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatusPedidoRabbitMQDTO {

    private Integer pedidoId;
    private LocalDateTime dataHora;
    private StatusPedidoEnum novoStatus;
}
