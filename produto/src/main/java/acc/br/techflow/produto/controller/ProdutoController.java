package acc.br.techflow.produto.controller;

import acc.br.techflow.produto.entity.Produto;
import acc.br.techflow.produto.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // Cadastra um novo produto
    @PostMapping
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        // Chama o serviço para salvar o produto e criar o estoque
        Produto produtoCadastrado = service.salvarComEstoque(produto);
        return ResponseEntity.status(201).body(produtoCadastrado);
    }

    // Lista todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    // Consulta um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> consultarPorID(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorID(id));
    }

    // Atualiza os dados de um produto pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return ResponseEntity.ok(service.atualizar(id, produto));
    }

    // Remove um produto pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.ok().build();
    }
}
