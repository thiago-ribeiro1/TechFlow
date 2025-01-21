package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
