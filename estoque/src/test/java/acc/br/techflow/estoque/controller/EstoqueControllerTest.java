package acc.br.techflow.estoque.controller;

import acc.br.techflow.estoque.dtoRabbit.ItemPedidoRabbitMQDTO;
import acc.br.techflow.estoque.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EstoqueController.class)
class EstoqueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstoqueService estoqueService;

    List<ItemPedidoRabbitMQDTO> itensPedido;

    @BeforeEach
    public void setUp() {

        itensPedido = Arrays.asList(
                new ItemPedidoRabbitMQDTO(1, 10),
                new ItemPedidoRabbitMQDTO(2, 5)
        );
    }

    @Test
    @DisplayName("Deve retornar 'true' se a validação do estoque foi bem-sucedida")
    void testValidarEstoqueComSucesso() throws Exception {
        when(estoqueService.validar(itensPedido)).thenReturn(true);

        mockMvc.perform(post("/api/estoque/validar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"produtoId\": 1, \"quantidade\": 10}, {\"produtoId\": 2, \"quantidade\": 5}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Deve retornar 'false' se a validação do estoque fracassou devido a quantidade insuficiente de produtos")
    void testValidarEstoqueComFalha() throws Exception {
        when(estoqueService.validar(itensPedido)).thenReturn(false);

        mockMvc.perform(post("/api/estoque/validar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"produtoId\": 1, \"quantidade\": 30}, {\"produtoId\": 2, \"quantidade\": 15}]"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}