package acc.br.techflow.produto.repository;

import acc.br.techflow.produto.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
