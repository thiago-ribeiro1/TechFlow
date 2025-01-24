package acc.br.techflow.pedido.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigFilas {

    @Bean
    public Queue criarFilaPagamentoNovoPedido() {
        return QueueBuilder.durable("oito.fl.pagamento.novo-pedido").build();
    }

    @Bean
    public Queue criarFilaEstoquePedidoPago() {
        return QueueBuilder.durable("oito.fl.estoque.pedido-pago").build();
    }

    @Bean
    public Queue criarFilaEnvioRetiradaAprovada() {
        return QueueBuilder.durable("oito.fl.envio.retirada-aprovada").build();
    }

    @Bean
    public Queue criarFilaStatusPedidoNovoStatus() {
        return QueueBuilder.durable("oito.fl.pedido.novo-status").build();
    }
}
