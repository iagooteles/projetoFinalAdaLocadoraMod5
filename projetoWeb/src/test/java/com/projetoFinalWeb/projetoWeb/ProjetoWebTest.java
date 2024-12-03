package com.projetoFinalWeb.projetoWeb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ProjetoWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjetoWebTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void deve_retornar_um_helloWorld_no_endpoint() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/helloWorld")) // mudar para a minha aplicação! //
//                .andExpect(status().isOk());
//    }
}
