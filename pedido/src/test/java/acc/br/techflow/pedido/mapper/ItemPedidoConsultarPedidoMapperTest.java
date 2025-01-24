package acc.br.techflow.pedido.mapper;

import acc.br.techflow.pedido.dominio.ItemPedido;
import acc.br.techflow.pedido.dto.resposta.ItemPedidoConsultarPedidoResposta;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class ItemPedidoConsultarPedidoMapperTest {

    @Test
    @DisplayName("Deve retornar uma lista de ItemPedidoConsultarPedidoResposta quando passado uma lista de ItemPedido")
    void deveRetornarUmaListaDeItemPedidoConsultarPedidoResposta() {
        List<ItemPedido> itensPedido = Instancio.ofList(ItemPedido.class).create();
        BigDecimal valorTotalItemEsperado = itensPedido.get(0).getProduto().getValor().multiply(BigDecimal.valueOf(itensPedido.get(0).getQuantidadeProduto()));

        List<ItemPedidoConsultarPedidoResposta> itensPedidoResposta = ItemPedidoConsultarPedidoMapper.converterListaEntidadeParaListaDTOResposta(itensPedido);

        Assertions.assertNotNull(itensPedidoResposta);
        Assertions.assertEquals(itensPedido.get(0).getProduto().getId(), itensPedidoResposta.get(0).getProdutoId());
        Assertions.assertEquals(itensPedido.get(0).getProduto().getValor(), itensPedidoResposta.get(0).getValorProduto());
        Assertions.assertEquals(itensPedido.get(0).getQuantidadeProduto(), itensPedidoResposta.get(0).getQuantidadeProduto());
        Assertions.assertEquals(valorTotalItemEsperado, itensPedidoResposta.get(0).getValorProduto().multiply(BigDecimal.valueOf(itensPedidoResposta.get(0).getQuantidadeProduto())));
    }
}