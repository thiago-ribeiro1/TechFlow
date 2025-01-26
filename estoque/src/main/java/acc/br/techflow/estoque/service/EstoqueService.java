package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dto.ItemPedido;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
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

    public List<ItemPedidoRabbitMQDTO> buildItensPedido(List<Integer> produtoId, List<Integer> quantidadeProduto) {
        List<ItemPedidoRabbitMQDTO> itensPedido = new ArrayList<>();
        for (int i = 0; i < produtoId.size(); i++) {
            itensPedido.add(new ItemPedidoRabbitMQDTO(produtoId.get(i), quantidadeProduto.get(i)));
        }
        return itensPedido;
    }

    public void atualizarEstoque(List<ItemPedidoRabbitMQDTO> itensPedido) {

        for (ItemPedidoRabbitMQDTO item : itensPedido) {
            Integer produtoId = item.getProdutoId();
            Integer quantidadeVendida = item.getQuantidade();

            Optional<Estoque> estoqueOptional = estoqueRepository.findByProdutoId(produtoId);

            if (estoqueOptional.isPresent()) {
                Estoque estoque = estoqueOptional.get();
                Integer quantidadeAtualizada = estoque.getQuantidade() - quantidadeVendida;
                estoque.setQuantidade((quantidadeAtualizada));
                estoqueRepository.save(estoque);
            }
        }
    }

    public void processarPedidoEstoque(List<Integer> produtoIds, List<Integer> quantidades) {
        List<ItemPedidoRabbitMQDTO> listaPedido = buildItensPedido(produtoIds, quantidades);
        atualizarEstoque(listaPedido);
    }

    public Boolean validar(List<ItemPedido> itensPedido){
        for(ItemPedido item : itensPedido){
            Integer produtoId = item.getProdutoId();
            Integer quantidadeSolicitada = item.getQuantidadeSolicitada();
            Optional<Estoque> estoque = estoqueRepository.findByProdutoId(produtoId);
            if(estoque.isEmpty() || estoque.get().getQuantidade() < quantidadeSolicitada) {
                return false;
            }
        }
        return true;
    }
}