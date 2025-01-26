package acc.br.techflow.cliente.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @DisplayName("Deve criar um cliente usando um construtor padrão")
    @Test
    void deveCriarClienteSemID() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setCpf("12345678901");

        assertNull(cliente.getId(), "O ID do cliente deve ser nulo inicialmente, pois será gerado pelo banco");
        assertEquals("João", cliente.getNome(), "O nome do cliente deve ser João");
        assertEquals("12345678901", cliente.getCpf(), "O CPF do cliente deve ser 12345678901");
    }

    @DisplayName("Deve criar um cliente usando o construtor completo")
    @Test
    void deveCriarClienteComConstrutorCompleto() {
        Cliente cliente = new Cliente(null, "Maria", "98765432100");

        assertNull(cliente.getId(), "O ID do cliente deve ser nulo, pois será gerado pelo banco");
        assertEquals("Maria", cliente.getNome(), "O nome do cliente deve ser Maria");
        assertEquals("98765432100", cliente.getCpf(), "O CPF do cliente deve ser 98765432100");
    }

    @DisplayName("Deve validar o comportamento de toString e hashCode")
    @Test
    void deveValidarToStringEHashCode() {
        Cliente cliente = new Cliente(null, "João", "12345678901");

        String esperadoToString = "Cliente(id=null, nome=João, cpf=12345678901)";
        assertEquals(esperadoToString, cliente.toString(), "O método toString deve retornar o formato correto");

    }
}
