package acc.br.techflow.produto.exception;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Inclui o controlador auxiliar para lançar exceções
@WebMvcTest(controllers = {AdviceController.class, TestController.class})
class AdviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Deve retornar BAD_REQUEST com detalhes do erro ao lançar RuntimeException")
    @Test
    void deveTratarRuntimeException() throws Exception {
        // Simula uma requisição que resultará em uma RuntimeException
        mockMvc.perform(get("/api/clientes/error") // Rota configurada no TestController
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dataHora", notNullValue())) // Valida que o campo dataHora não é nulo
                .andExpect(jsonPath("$.status", is(400))) // Valida o status 400
                .andExpect(jsonPath("$.motivo", is("Bad Request"))) // Valida o motivo correto
                .andExpect(jsonPath("$.mensagem", is("Erro simulado para teste"))) // Mensagem de erro lançada
                .andExpect(jsonPath("$.path", is("/api/produtos"))); // Valida o endpoint
    }
}
