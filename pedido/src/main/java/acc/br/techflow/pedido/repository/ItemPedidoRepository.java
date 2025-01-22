package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    List<ItemPedido> findAllByPedidoId(Integer pedidoId);
}
