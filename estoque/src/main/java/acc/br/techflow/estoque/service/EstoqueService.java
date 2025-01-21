package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dto.ItemPedidoDTO;
import acc.br.techflow.estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public Boolean validar(List<ItemPedidoDTO> itensPedido) {
        for (ItemPedidoDTO item : itensPedido) {
            Integer produtoId = item.getProdutoId();
            Integer quantidadeSolicitada = item.getQuantidadeProduto();

            Optional<Estoque> estoque = estoqueRepository.findByProdutoId(produtoId);

            if (estoque.isEmpty() || estoque.get().getQuantidade() < quantidadeSolicitada) {
                return false;
            }
        }
        return true;
    }

    public Boolean validarListaPedidos(List<Integer> produtoId, List<Integer> quantidadeProduto) throws IllegalAccessException {
        if (produtoId.size() != quantidadeProduto.size()) {
            throw new IllegalAccessException("Número de produtos e quantidades não são compatíveis");
        }
        List<ItemPedidoDTO> itensPedido = buildItensPedido(produtoId, quantidadeProduto);
        return validar(itensPedido);
    }

    public List<ItemPedidoDTO> buildItensPedido(List<Integer> produtoId, List<Integer> quantidadeProduto) {
        List<ItemPedidoDTO> itensPedido = new ArrayList<>();
        for (int i = 0; i < produtoId.size(); i++) {
            itensPedido.add(new ItemPedidoDTO(produtoId.get(i), quantidadeProduto.get(i)));
        }
        return itensPedido;
    }

    public void atualizarEstoque(List<ItemPedidoDTO> itensPedido) {
        if (!validar(itensPedido)) {
            throw new IllegalArgumentException("Estoque insuficiente para um ou mais produtos.");
        }
        for (ItemPedidoDTO item : itensPedido) {
            Integer produtoId = item.getProdutoId();
            Integer quantidadeVendida = item.getQuantidadeProduto();

            Optional<Estoque> estoqueOptional = estoqueRepository.findByProdutoId(produtoId);

            if (estoqueOptional.isPresent()) {
                Estoque estoque = estoqueOptional.get();
                Integer quantidadeAtualizada = estoque.getQuantidade() - quantidadeVendida;
                estoque.setQuantidade((quantidadeAtualizada));
                estoqueRepository.save(estoque);
            }
        }
    }
}
