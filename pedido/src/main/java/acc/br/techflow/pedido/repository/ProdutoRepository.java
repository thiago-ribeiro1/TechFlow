package acc.br.techflow.pedido.repository;

import acc.br.techflow.pedido.dominio.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
