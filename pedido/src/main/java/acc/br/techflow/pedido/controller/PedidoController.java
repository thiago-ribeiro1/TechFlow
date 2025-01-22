package acc.br.techflow.pedido.controller;

import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosResposta;
import acc.br.techflow.pedido.service.ListarTodosPedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private ListarTodosPedidosService listarTodosPedidosService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ListarTodosPedidosResposta> listarTodos() {
        return listarTodosPedidosService.listar();
    }
}
