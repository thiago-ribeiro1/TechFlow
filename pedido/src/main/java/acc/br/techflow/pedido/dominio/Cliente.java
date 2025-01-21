package acc.br.techflow.pedido.dominio;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;
}
