package acc.br.techflow.pedido.dto.requisicao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemPedidoCadastrarPedidoRequisicao {

    @NotNull(message = "É necessário informar o ID do produto na lista de itens do pedido")
    private Integer produtoId;

    @NotNull(message = "É necessário informar a quantidade do produto na lista de itens do pedido")
    @Min(value = 1, message = "A quantidade do produto deve ser maior que 0")
    private Integer quantidade;
}
