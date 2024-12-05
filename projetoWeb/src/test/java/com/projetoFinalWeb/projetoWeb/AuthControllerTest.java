package com.projetoFinalWeb.projetoWeb;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProjetoWebApplication.class)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void register_shouldAddAndReturnToken() {
        String registerPayload = """
            {
                "login": "Lebron James",
                "password": "betterThanJordan?",
                "email": "kingjames@gmail.com"
            }
        """;

        given()
                .body(registerPayload)
                .contentType("application/json")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(200)
                .body(equalTo("Usuario registrado"));
    }

    // fazer os outros testes
}
