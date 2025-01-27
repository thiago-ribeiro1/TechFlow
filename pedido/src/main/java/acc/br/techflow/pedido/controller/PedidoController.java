package acc.br.techflow.pedido.controller;

import acc.br.techflow.pedido.dto.requisicao.CadastrarPedidoRequisicao;
import acc.br.techflow.pedido.dto.resposta.CadastrarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.ConsultarPedidoResposta;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosPorClienteResposta;
import acc.br.techflow.pedido.dto.resposta.ListarTodosPedidosResposta;
import acc.br.techflow.pedido.service.CadastrarPedidoService;
import acc.br.techflow.pedido.service.ConsultarPedidoService;
import acc.br.techflow.pedido.service.ListarTodosPedidosPorClienteService;
import acc.br.techflow.pedido.service.ListarTodosPedidosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pedidos")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private CadastrarPedidoService cadastrarPedidoService;

    @Autowired
    private ListarTodosPedidosService listarTodosPedidosService;

    @Autowired
    private ListarTodosPedidosPorClienteService listarTodosPedidosPorClienteService ;

    @Autowired
    private ConsultarPedidoService consultarPedidoService;

    @Operation(summary = "Cadastra um novo pedido", description = "Cadastra um novo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados da requisição mal formulados")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @ApiResponse(responseCode = "500", description = "Problemas internos no servidor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CadastrarPedidoResposta cadastrar(@Valid @RequestBody CadastrarPedidoRequisicao requisicao) {
        return cadastrarPedidoService.cadastrar(requisicao);
    }

    @Operation(summary = "Lista todos os pedidos", description = "Lista todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Listagem de pedidos feita com sucesso")
    @ApiResponse(responseCode = "500", description = "Problemas internos no servidor")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ListarTodosPedidosResposta> listarTodos() {
        return listarTodosPedidosService.listar();
    }

    @Operation(summary = "Lista todos os pedidos de um cliente específico", description = "Lista todos os pedidos de um cliente específico")
    @ApiResponse(responseCode = "200", description = "Listagem de pedidos feita com sucesso")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @ApiResponse(responseCode = "500", description = "Problemas internos no servidor")
    @GetMapping("/cliente/{clienteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ListarTodosPedidosPorClienteResposta> listarTodosPorCliente(@PathVariable Integer clienteId) {
        return listarTodosPedidosPorClienteService.listar(clienteId);
    }

    @Operation(summary = "Retorna, através do ID do pedido, as informações detalhadas de um pedido específico", description = "Retorna, através do ID do pedido, as informações detalhadas de um pedido específico")
    @ApiResponse(responseCode = "200", description = "Dados do pedido retornado com sucesso")
    @ApiResponse(responseCode = "404", description = "Dado não encontrado")
    @ApiResponse(responseCode = "500", description = "Problemas internos no servidor")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultarPedidoResposta consultar(@PathVariable Integer id) {
        return consultarPedidoService.consultar(id);
    }
}
