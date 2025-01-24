package acc.br.techflow.pedido.service.consultar;

import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultarClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void validarPorId(Integer clienteId) {
        boolean clienteCadastrado = clienteRepository.existsById(clienteId);

        if(!clienteCadastrado) throw new DadoNaoEncontradoException("Cliente não encontrado");
    }
}
