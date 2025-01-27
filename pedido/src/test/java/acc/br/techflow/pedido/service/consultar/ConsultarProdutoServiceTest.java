package acc.br.techflow.pedido.service.consultar;

import acc.br.techflow.pedido.dominio.Produto;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.ProdutoRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarProdutoServiceTest {

    @InjectMocks
    private ConsultarProdutoService servicoTestado;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve retornar um produto quando o ID enviado corresponder ao ID de um produto já cadastrado no sistema")
    void deveRetornarUmProdutoQuandoProdutoCadastrado() {
        Integer produtoId = 1;
        Produto produto = Instancio.of(Produto.class).create();

        when(produtoRepository.findById(produtoId))
                .thenReturn(Optional.ofNullable(produto));

        Produto resposta = servicoTestado.consultarPorId(produtoId);

        verify(produtoRepository).findById(produtoId);

        assertNotNull(resposta);
    }

    @Test
    @DisplayName("Deve retornar erro quando o ID enviado não corresponder ao ID de um cliente já cadastrado no sistema")
    void deveRetornarErroQuandoIdClienteInvalido() {
        Integer produtoId = 1;

        DadoNaoEncontradoException exception =
                assertThrows(DadoNaoEncontradoException.class, () -> servicoTestado.consultarPorId(produtoId));

        verify(produtoRepository).findById(produtoId);

        assertEquals("Produto de ID " + produtoId + " não encontrado", exception.getMessage());
    }
}