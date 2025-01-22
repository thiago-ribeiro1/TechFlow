package acc.br.techflow.pedido.controller;

import acc.br.techflow.pedido.dto.resposta.ConsultarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosPorClienteResposta;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosResposta;
import acc.br.techflow.pedido.service.ConsultarPedidoService;
import acc.br.techflow.pedido.service.ListarTodosPedidosPorClienteService;
import acc.br.techflow.pedido.service.ListarTodosPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private ListarTodosPedidosService listarTodosPedidosService;

    @Autowired
    private ListarTodosPedidosPorClienteService listarTodosPedidosPorClienteService ;

    @Autowired
    private ConsultarPedidoService consultarPedidoService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ListarTodosPedidosResposta> listarTodos() {
        return listarTodosPedidosService.listar();
    }

    @GetMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ListarTodosPedidosPorClienteResposta> listarTodosPorCliente(@PathVariable Integer clienteId) {
        return listarTodosPedidosPorClienteService.listar(clienteId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultarPedidoResposta consultar(@PathVariable Integer id) {
        return consultarPedidoService.consultar(id);
    }
}
