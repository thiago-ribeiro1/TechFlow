package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Integer> {

    List<StatusPedido> findAllByPedidoId(Integer pedidoId);
}
