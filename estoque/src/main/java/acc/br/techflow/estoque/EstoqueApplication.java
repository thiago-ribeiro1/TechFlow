package acc.br.techflow.estoque;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======

>>>>>>> 12ebf85d8202ddcbd34bc948396296ecc1b60743

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "TechFlow - Microsserviço de estoque", version = "1", description = "Microsserviço responsável por gerenciar o estoque do sistema."))
public class EstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}

}
