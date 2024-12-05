package com.projetoFinalWeb.projetoWeb;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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
                "role": "USER"
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

    @Test
    public void login_shouldReturnTokenForValidCredentials() {
        String loginPayload = """
                {
                    "login": "Lebron James",
                    "password": "betterThanJordan?"
                }
                """;

        given()
                .body(loginPayload)
                .contentType("application/json")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", not(emptyOrNullString()));
    }

    @Test
    public void login_shouldReturnUnauthorizedForInvalidCredentials() {
        String invalidLoginPayload = """
            {
                "login": "MichaelJordan",
                "password": "notLebronJames"
            }
        """;

        given()
                .body(invalidLoginPayload)
                .contentType("application/json")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(403);
    }
}
