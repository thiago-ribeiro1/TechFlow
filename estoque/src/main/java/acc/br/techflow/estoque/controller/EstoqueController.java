package acc.br.techflow.estoque.controller;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;
    @PostMapping("/validar")
    public Boolean validarEstoque(@RequestBody List<ItemPedidoRabbitMQDTO> itensPedido) {
        return estoqueService.validar(itensPedido);
    }
}