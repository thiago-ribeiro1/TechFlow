package acc.br.techflow.pedido.controller;

import acc.br.techflow.pedido.dto.resposta.ErroResposta;
import acc.br.techflow.pedido.exception.DadoNaoEncontradoException;
import acc.br.techflow.pedido.exception.DadoRepetidoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(DadoRepetidoException.class)
    public ResponseEntity<ErroResposta> handleDadoRepetidoException(DadoRepetidoException ex, HttpServletRequest request) {

        HttpStatus status = BAD_REQUEST;

        ErroResposta erroResposta = new ErroResposta();
        erroResposta.setDataHora(LocalDateTime.now());
        erroResposta.setStatus(status.value());
        erroResposta.setMotivo(status.getReasonPhrase());
        erroResposta.setMensagem(ex.getMessage());
        erroResposta.setPath(request.getServletPath());

        return new ResponseEntity<>(erroResposta, status);
    }

    @ExceptionHandler(DadoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleDadoNaoEncontradoException(DadoNaoEncontradoException ex, HttpServletRequest request) {

        HttpStatus status = NOT_FOUND;

        ErroResposta erroResposta = new ErroResposta();
        erroResposta.setDataHora(LocalDateTime.now());
        erroResposta.setStatus(status.value());
        erroResposta.setMotivo(status.getReasonPhrase());
        erroResposta.setMensagem(ex.getMessage());
        erroResposta.setPath(request.getServletPath());

        return new ResponseEntity<>(erroResposta, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {

        HttpStatus status = BAD_REQUEST;

        ErroResposta erroResposta = new ErroResposta();
        erroResposta.setDataHora(LocalDateTime.now());
        erroResposta.setStatus(status.value());
        erroResposta.setMotivo(status.getReasonPhrase());
        erroResposta.setMensagem("JSON mal formatado: valide se todos os campos estão com o formato correto ou se o JSON está formatado corretamente");
        erroResposta.setPath(request.getServletPath());

        return new ResponseEntity<>(erroResposta, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                              HttpServletRequest request) {
        HttpStatus status = BAD_REQUEST;
        String message = catchError(ex);

        ErroResposta erroResposta = new ErroResposta();
        erroResposta.setDataHora(LocalDateTime.now());
        erroResposta.setStatus(status.value());
        erroResposta.setMotivo(status.getReasonPhrase());
        erroResposta.setMensagem(message);
        erroResposta.setPath(request.getServletPath());

        return new ResponseEntity<>(erroResposta, status);
    }

    private String catchError(MethodArgumentNotValidException ex) {
        Optional<ObjectError> erroOpt = ex.getBindingResult().getAllErrors().stream()
                .findFirst();

        if (erroOpt.isEmpty()) {
            return "Erro de validação";
        }

        FieldError erro = (FieldError) erroOpt.get();
        return erro.getDefaultMessage();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleException(Exception ex, HttpServletRequest request) {

        HttpStatus status = INTERNAL_SERVER_ERROR;

        ErroResposta erroResposta = new ErroResposta();
        erroResposta.setDataHora(LocalDateTime.now());
        erroResposta.setStatus(status.value());
        erroResposta.setMotivo(status.getReasonPhrase());
        erroResposta.setMensagem(ex.getClass() + " / " + ex.getMessage()); //"Ocorreu algum problema interno no servidor"
        erroResposta.setPath(request.getServletPath());

        return new ResponseEntity<>(erroResposta, status);
    }
}
