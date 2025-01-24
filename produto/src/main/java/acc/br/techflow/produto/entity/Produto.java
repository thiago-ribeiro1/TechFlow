package acc.br.techflow.produto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    // Define o relacionamento bidirecional One-to-One entre Produto e Estoque
    // A propriedade "mappedBy" indica que a relação é controlada pelo atributo "produto" na classe Estoque
    // O "cascade = CascadeType.ALL" garante que operações em Produto (salvar, atualizar, remover) sejam propagadas para Estoque
    // O "orphanRemoval = true" remove automaticamente o registro de Estoque quando o Produto correspondente for excluído
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Indica que este é o lado "pai" da relação no banco
    private Estoque estoque;

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
        if (estoque != null) {
            estoque.setProduto(this); // Configura o relacionamento bidirecional
        }
    }
}
