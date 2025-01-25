package acc.br.techflow.pedido.service.consultar;

import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultarClienteServiceTest {

    @InjectMocks
    private ConsultarClienteService servicoTestado;

    @Mock
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Não deve fazer nada quando o cliente já tiver sido cadastrado no sistema")
    void naoDeveFazerNadaQuandoClienteCadastrado() {
        Integer clienteId = 1;

        when(clienteRepository.existsById(clienteId))
                .thenReturn(true);

        servicoTestado.validarPorId(clienteId);

        verify(clienteRepository).existsById(clienteId);
    }

    @Test
    @DisplayName("Deve retornar erro quando o cliente não tiver sido cadastrado no sistema")
    void deveRetornarErroQuandoClienteNaoCadastrado() {
        Integer clienteId = 1;

        when(clienteRepository.existsById(clienteId))
                .thenReturn(false);

        DadoNaoEncontradoException exception =
                assertThrows(DadoNaoEncontradoException.class, () -> servicoTestado.validarPorId(clienteId));

        verify(clienteRepository).existsById(clienteId);

        assertEquals("Cliente de ID "  + clienteId + " não encontrado", exception.getMessage());
    }
}