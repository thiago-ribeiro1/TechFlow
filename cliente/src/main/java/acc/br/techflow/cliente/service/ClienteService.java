package acc.br.techflow.cliente.service;

import acc.br.techflow.cliente.entity.Cliente;
import acc.br.techflow.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    public Cliente consultarPorID(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        // consulta o cliente pelo ID e guarda em uma variável para manipulá-lo
        Cliente existente = consultarPorID(id);
        existente.setNome(cliente.getNome());
        existente.setCpf(cliente.getCpf());
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
