package acc.br.techflow.pedido.dominio;

import acc.br.techflow.pedido.dominio.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "status_pedido")
public class StatusPedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public StatusPedido(StatusPedidoEnum status, LocalDateTime dataHora, Pedido pedido) {
        this.status = status;
        this.dataHora = dataHora;
        this.pedido = pedido;
    }
}
