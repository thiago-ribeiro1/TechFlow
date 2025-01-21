package acc.br.techflow.pedido.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal valor;
}
