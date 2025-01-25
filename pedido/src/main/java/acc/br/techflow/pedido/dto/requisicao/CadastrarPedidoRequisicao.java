package acc.br.techflow.pedido.dto.requisicao;

import acc.br.techflow.pedido.dominio.enums.MetodoPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CadastrarPedidoRequisicao {

    @NotEmpty(message = "A lista de itens do pedido não pode estar vazia")
    @Valid
    private List<ItemPedidoCadastrarPedidoRequisicao> itensPedido;

    private String observacao;

    @NotNull(message = "É necessário informar o método de pagamento")
    private MetodoPagamento metodoPagamento;

    @NotBlank(message = "É necessário informar o endereço")
    @Size(max = 300, message = "O endereço não pode ter mais que 300 caracteres")
    private String endereco;

    @NotNull(message = "É necessário informar o ID do cliente")
    private Integer clienteId;
}
