package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findAllByClienteId(Integer clienteId);
}
