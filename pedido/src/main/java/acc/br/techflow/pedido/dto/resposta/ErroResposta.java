package acc.br.techflow.pedido.dto.resposta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErroResposta {

    private LocalDateTime dataHora;

    private int status;

    private String motivo;

    private String mensagem;

    private String path;
}
