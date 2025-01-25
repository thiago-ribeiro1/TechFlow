package acc.br.techflow.pedido.service.consultar;

import acc.br.techflow.pedido.dominio.Produto;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultarProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto consultarPorId(Integer produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new DadoNaoEncontradoException("Produto não encontrado"));
    }
}
