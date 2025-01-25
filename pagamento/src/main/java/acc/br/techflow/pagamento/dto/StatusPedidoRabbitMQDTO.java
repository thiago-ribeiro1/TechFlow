package acc.br.techflow.pagamento.dto;

import acc.br.techflow.pagamento.enums.StatusPedidoEnum;
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

