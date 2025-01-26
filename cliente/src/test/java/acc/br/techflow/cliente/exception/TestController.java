package acc.br.techflow.cliente.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class TestController {

    @GetMapping("/error")
    public void simularErro() {
        throw new RuntimeException("Erro simulado para teste");
    }
}
