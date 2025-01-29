package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.dtoRabbit.PedidoRabbitMQDTO;
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

    public void atualizarEstoque(List<ItemPedidoRabbitMQDTO> itensPedido) {

        for (ItemPedidoRabbitMQDTO item : itensPedido) {
            Integer produtoId = item.getProdutoId();
            Integer quantidadeVendida = item.getQuantidade();

            Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto inválido"));

            Integer quantidadeAtualizada = estoque.getQuantidade() - quantidadeVendida;
            estoque.setQuantidade(quantidadeAtualizada);
            estoqueRepository.save(estoque);
        }
    }

    public Boolean validar(List<ItemPedidoRabbitMQDTO> itensPedido){
        for(ItemPedidoRabbitMQDTO item : itensPedido){
            Integer produtoId = item.getProdutoId();
            Integer quantidadeSolicitada = item.getQuantidade();
            Optional<Estoque> estoque = estoqueRepository.findByProdutoId(produtoId);
            if(estoque.isEmpty() || estoque.get().getQuantidade() < quantidadeSolicitada) {
                return false;
            }
        }
        return true;
    }
}