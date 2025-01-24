package acc.br.techflow.pedido.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigBindings {

    @Autowired
    private RabbitMQConfigFilas configFilas;

    @Autowired
    private RabbitMQConfigExchanges configExchanges;

    @Bean
    public Binding criarBindingExPedidoFlNovoPedido() {
        return BindingBuilder.bind(configFilas.criarFilaPagamentoNovoPedido())
                .to(configExchanges.criarDirectExchangePedido())
                .with("oito.novo.pedido");
    }

    @Bean
    public Binding criarBindingExPedidoFlPedidoPago() {
        return BindingBuilder.bind(configFilas.criarFilaEstoquePedidoPago())
                .to(configExchanges.criarDirectExchangePedido())
                .with("oito.pedido.pago");
    }

    @Bean
    public Binding criarBindingExPedidoFlRetiradaAprovada() {
        return BindingBuilder.bind(configFilas.criarFilaEnvioRetiradaAprovada())
                .to(configExchanges.criarDirectExchangePedido())
                .with("oito.retirada.aprovada");
    }

    @Bean
    public Binding criarBindingExStatusPedidoFlNovoStatus() {
        return BindingBuilder.bind(configFilas.criarFilaStatusPedidoNovoStatus())
                .to(configExchanges.criarFanoutExchangeStatusPedido());
    }
}
