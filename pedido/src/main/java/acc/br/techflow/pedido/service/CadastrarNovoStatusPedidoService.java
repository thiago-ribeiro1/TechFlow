package acc.br.techflow.pedido.service;

import acc.br.techflow.pedido.dominio.Pedido;
import acc.br.techflow.pedido.dominio.StatusPedido;
import acc.br.techflow.pedido.dto.rabbitmq.StatusPedidoRabbitMQDTO;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.PedidoRepository;
import acc.br.techflow.pedido.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastrarNovoStatusPedidoService {

    @Autowired
    private StatusPedidoRepository statusPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void cadastrar(StatusPedidoRabbitMQDTO dtoRabbitMQ) {
        Pedido pedido = pedidoRepository.findById(dtoRabbitMQ.getPedidoId())
                .orElseThrow(() -> new DadoNaoEncontradoException("Pedido de ID " + dtoRabbitMQ.getPedidoId() + " não encontrado"));

        StatusPedido statusPedido = new StatusPedido(dtoRabbitMQ.getNovoStatus(), dtoRabbitMQ.getDataHora(), pedido);

        statusPedidoRepository.save(statusPedido);
    }
}
