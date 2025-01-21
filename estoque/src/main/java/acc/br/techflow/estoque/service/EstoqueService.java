package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dto.ItemPedidoDTO;
import acc.br.techflow.estoque.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public Boolean validar(List<ItemPedidoDTO> itensPedido){
        for(ItemPedidoDTO item : itensPedido){
            Integer produtoId = item.getProdutoId();
            Integer quantidadeSolicitada = item.getQuantidadeProduto();

           Optional<Estoque> estoque = estoqueRepository.findByProdutoId(produtoId);

           if(estoque.isEmpty() || estoque.get().getQuantidade() < quantidadeSolicitada) {
               return false;
           }
        }
        return true;
    }

    public void atualizarEstoque(List<ItemPedidoDTO> itensPedido){
        for(ItemPedidoDTO item: itensPedido){
            Integer produtoId = item.getProdutoId();
            Integer quantidadeVendida = item.getQuantidadeProduto();

            Optional<Estoque> estoqueOptional = estoqueRepository.findByProdutoId(produtoId);

            if(estoqueOptional.isPresent()){
                Estoque estoque = estoqueOptional.get();
                Integer quantidadeAtualizada = estoque.getQuantidade() - quantidadeVendida;

                estoque.setQuantidade((quantidadeAtualizada));
                estoqueRepository.save(estoque);
            }
        }
    }
}
