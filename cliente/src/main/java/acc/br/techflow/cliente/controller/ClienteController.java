package acc.br.techflow.cliente.controller;

import acc.br.techflow.cliente.entity.Cliente;
import acc.br.techflow.cliente.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // Cadastra um novo cliente
    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody Cliente cliente) {
        Cliente clienteCadastrado = service.salvar(cliente);
        return ResponseEntity.status(201).body(clienteCadastrado);
    }

    // Lista todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    // Consulta um cliente pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> consultarPorID(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorID(id));
    }

    // Atualiza os dados de um cliente pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return ResponseEntity.ok(service.atualizar(id, cliente));
    }

    // Remove um cliente pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.ok().build();
    }
}
