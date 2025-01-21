package acc.br.techflow.estoque.controller;

import acc.br.techflow.estoque.dto.ItemPedidoDTO;
import acc.br.techflow.estoque.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public Boolean validarEstoque(@RequestParam List<Integer> produtoId, @RequestParam List<Integer> quantidadeProduto) throws IllegalAccessException {
        if(produtoId.size() != quantidadeProduto.size()){
            throw new IllegalAccessException("Número de produtos e quantidades não são compatíveis");
        }

        List<ItemPedidoDTO> itensPedido = new ArrayList<>();
        for(int i = 0; i < produtoId.size(); i++){
            itensPedido.add(new ItemPedidoDTO(produtoId.get(i), quantidadeProduto.get(i)));
        }
        return estoqueService.validar(itensPedido);
    }
}
