package acc.br.techflow.estoque.repository;

import acc.br.techflow.estoque.dominio.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByProdutoId(Integer produtoId);
}
