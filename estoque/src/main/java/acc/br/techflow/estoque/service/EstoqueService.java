package acc.br.techflow.estoque.service;

import acc.br.techflow.estoque.dominio.Estoque;
import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.exception.RegraNegocioException;
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

    //Preciso validar se tem quantidade suficiente no estoque para atender todos os itens do pedido?
    public Boolean validar(List<ItemPedidoRabbitMQDTO> itensPedido) {
        for (ItemPedidoRabbitMQDTO item : itensPedido) {
            Integer produtoId = item.getProdutoId();
            Integer quantidadeSolicitada = item.getQuantidade();

            Optional<Estoque> estoque = estoqueRepository.findByProdutoId(produtoId);

            if (estoque.isEmpty() || estoque.get().getQuantidade() < quantidadeSolicitada) {
                return false;
            }
        }
        return true;
    }

    //Por http, caso passe quantidades diferentes de parâmetros ex 3 produtos e 1 quantidade só?
    public Boolean validarListaPedidos(List<Integer> produtoId, List<Integer> quantidadeProduto) {
        if (produtoId.size() != quantidadeProduto.size()) {
            throw new RegraNegocioException ("Número de produtos e quantidades não são compatíveis");
        }
        List<ItemPedidoRabbitMQDTO> itensPedido = buildItensPedido(produtoId, quantidadeProduto);
        return validar(itensPedido);
    }

    // O metodo acima usa
    public List<ItemPedidoRabbitMQDTO> buildItensPedido(List<Integer> produtoId, List<Integer> quantidadeProduto) {
        List<ItemPedidoRabbitMQDTO> itensPedido = new ArrayList<>();
        for (int i = 0; i < produtoId.size(); i++) {
            itensPedido.add(new ItemPedidoRabbitMQDTO(produtoId.get(i), quantidadeProduto.get(i)));
        }
        return itensPedido;
    }

    public void atualizarEstoque(List<ItemPedidoRabbitMQDTO> itensPedido) {
        //Preciso dessa validação?
        if (!validar(itensPedido)) {
            throw new RegraNegocioException("Estoque insuficiente para um ou mais produtos.");
        }
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
}