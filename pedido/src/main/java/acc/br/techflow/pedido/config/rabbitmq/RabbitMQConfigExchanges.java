package acc.br.techflow.pedido.config.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigExchanges {

    @Bean
    public DirectExchange criarDirectExchangePedido() {
        return ExchangeBuilder.directExchange("oito.ex.pedido").build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangeStatusPedido() {
        return ExchangeBuilder.fanoutExchange("oito.ex.status-pedido").build();
    }
}
