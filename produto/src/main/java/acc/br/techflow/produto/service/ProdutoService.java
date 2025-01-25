package acc.br.techflow.produto.service;

import acc.br.techflow.produto.entity.Estoque;
import acc.br.techflow.produto.entity.Produto;
import acc.br.techflow.produto.repository.ProdutoRepository;
import acc.br.techflow.produto.repository.EstoqueRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProdutoService(ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public Produto salvarComEstoque(Produto produto) {
        // Salva o produto no banco e garante que o ID seja gerado
        Produto produtoSalvo = produtoRepository.save(produto);

        // Obtém ou cria o estoque associado ao produto
        Estoque estoque = produto.getEstoque();
        if (estoque == null) {
            // Cria um estoque padrão caso nenhum seja fornecido
            estoque = new Estoque();
            estoque.setQuantidade(0); // Quantidade padrão
        }

        // Associa o produto salvo ao estoque
        estoque.setProduto(produtoSalvo);

        // Salva o estoque no banco
        estoqueRepository.save(estoque);

        // Associa o estoque salvo ao produto (opcional, útil para retorno ao cliente)
        produtoSalvo.setEstoque(estoque);

        return produtoSalvo;
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Produto consultarPorID(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizar(Long id, Produto produto) {
        // Consulta o produto existente
        Produto existente = consultarPorID(id);

        // Atualiza os campos do produto
        existente.setNome(produto.getNome());
        existente.setDescricao(produto.getDescricao());
        existente.setValor(produto.getValor());

        // Verifica se o estoque foi fornecido no objeto do produto
        if (produto.getEstoque() != null && produto.getEstoque().getQuantidade() != null) {
            // Atualiza a quantidade no estoque
            Estoque estoqueExistente = existente.getEstoque();
            estoqueExistente.setQuantidade(produto.getEstoque().getQuantidade());
            estoqueRepository.save(estoqueExistente);
        }

        // Salva o produto atualizado
        return produtoRepository.save(existente);
    }

    public void remover(Long id) {
        produtoRepository.deleteById(id);
    }
}
