package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.listartodos.ListarTodosPedidosResposta;
import acc.br.techflow.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTodosPedidosService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<ListarTodosPedidosResposta> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return null;
    }
}
