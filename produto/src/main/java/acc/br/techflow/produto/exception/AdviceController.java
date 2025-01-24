package acc.br.techflow.produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

// Trata exceções globais da aplicação
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> DadoNaoEncontradoException(RuntimeException ex) {
        Map<String, Object> body = Map.of(
                "dataHora", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "motivo", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "mensagem", ex.getMessage(),
                "path", "/api/produtos"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
