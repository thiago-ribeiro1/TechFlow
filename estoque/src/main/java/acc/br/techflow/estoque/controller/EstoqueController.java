package acc.br.techflow.estoque.controller;

import acc.br.techflow.estoque.dto.ItemPedidoDTO;
import acc.br.techflow.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public Boolean validarEstoque(@RequestParam List<Integer> produtoId, @RequestParam List<Integer> quantidadeProduto) throws IllegalAccessException {
        List<ItemPedidoDTO> listaPedido = estoqueService.buildItensPedido(produtoId, quantidadeProduto);
        estoqueService.atualizarEstoque(listaPedido);
        return estoqueService.validarListaPedidos(produtoId, quantidadeProduto);
    }
}