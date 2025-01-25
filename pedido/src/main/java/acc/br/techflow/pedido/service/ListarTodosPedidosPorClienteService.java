package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosPorClienteResposta;
import acc.br.techflow.pedido.mapper.ListarTodosPedidosPorClienteMapper;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.service.consultar.ConsultarClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarTodosPedidosPorClienteService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ConsultarClienteService consultarClienteService;

    public List<ListarTodosPedidosPorClienteResposta> listar(Integer clienteId) {
        consultarClienteService.validarPorId(clienteId);

        List<Pedido> todosPedidosPorCliente = pedidoRepository.findAllByClienteId(clienteId);

        return retornarListaDTOListarTodosPedidosPorClienteResposta(todosPedidosPorCliente);
    }

    private List<ListarTodosPedidosPorClienteResposta> retornarListaDTOListarTodosPedidosPorClienteResposta(List<Pedido> listaPedidosPorCliente) {
        return listaPedidosPorCliente.stream().map(pedido -> {
            ListarTodosPedidosPorClienteResposta pedidoResposta = ListarTodosPedidosPorClienteMapper.INSTANCIA.converterEntidadeParaDTOResposta(pedido);
            pedidoResposta.setClienteId(pedido.getCliente().getId());
            return pedidoResposta;
        }).toList();
    }
}
