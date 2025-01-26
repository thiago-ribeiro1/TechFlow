package acc.br.techflow.pedido.openfeign;

import acc.br.techflow.pedido.dto.openfeign.ItemPedidoOpenFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "estoque", url= "http://localhost:8083/api/estoque")
public interface EstoqueOpenFeign {

    @RequestMapping(method = RequestMethod.POST, path="/validar")
    Boolean validarEstoque(@RequestBody List<ItemPedidoOpenFeignDTO> itensPedido);
}
