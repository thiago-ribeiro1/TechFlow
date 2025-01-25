package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
