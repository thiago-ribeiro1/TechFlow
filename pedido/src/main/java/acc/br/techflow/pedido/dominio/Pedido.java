package acc.br.techflow.pedido.dominio;

import acc.br.techflow.pedido.dominio.enums.MetodoPagamento;
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
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private LocalDateTime dataHora;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    private String endereco;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
