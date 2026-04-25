package dev.weriton.patterns.realworld;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.ApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testes de Inicialização do Spring Boot")
@SpringBootTest
@Testcontainers
class RealWorldApplicationTests {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18-alpine");

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Deve carregar o contexto da aplicação com sucesso e conectar ao banco efêmero")
    void contextLoads() {
        assertNotNull(applicationContext, "O contexto do Spring Boot não deveria ser nulo");
    }

    @Test
    @DisplayName("Deve executar o método main da aplicação com sucesso")
    void mainLoads() {
        assertDoesNotThrow(() -> {
            RealWorldApplication.main(new String[]{
                    "--server.port=0",
                    "--spring.datasource.url=" + postgres.getJdbcUrl(),
                    "--spring.datasource.username=" + postgres.getUsername(),
                    "--spring.datasource.password=" + postgres.getPassword()
            });
        }, "A execução do método main falhou e lançou uma exceção");
    }
}
