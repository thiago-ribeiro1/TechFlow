package acc.br.techflow.estoque.dominio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Integer quantidade;

    @NonNull
    private Integer produtoId;

    public @NonNull Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@NonNull Integer quantidade) {
        this.quantidade = quantidade;
    }

}
