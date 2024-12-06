package com.projetoFinalWeb.projetoWeb.integrationTest;

import com.projetoFinalWeb.projetoWeb.dto.ResponsePagingDTO;
import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import com.projetoFinalWeb.projetoWeb.repository.VeiculoRepository;
import com.projetoFinalWeb.projetoWeb.service.VeiculoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VeiculoControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void listarTodosDeveRetornarStatus200() throws Exception {
        List<VeiculoDTO> veiculos = Arrays.asList(
                new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00")),
                new VeiculoDTO("DEF5678", "Marca Y", "Modelo B", "Owner2", true, new BigDecimal("150.00"), new BigDecimal("25.00"))
        );
        when(veiculoService.listarTodos()).thenReturn(veiculos);

        mockMvc.perform(get("/veiculos/listAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // Pendente
    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void listarTodosPagingDeveRetornarStatus200() throws Exception {
        ResponsePagingDTO<VeiculoDTO> response = ResponsePagingDTO.<VeiculoDTO>builder()
                .message("Success")
                .list(Arrays.asList(new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"))))
                .pageNumber(1)
                .offset(10)
                .build();

        System.out.println(veiculoService.listarTodosPaging(0, 10).getClass());

        when(veiculoService.listarTodosPaging(Mockito.anyInt(), Mockito.anyInt())).thenReturn(response);

        mockMvc.perform(get("/veiculos/all-paging")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list.length()").value(1));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void buscarPorPlacaDeveRetornarStatus200() throws Exception {
        Veiculo veiculo = new Veiculo("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"));
        when(veiculoService.buscarPorPlaca("ABC1234")).thenReturn(veiculo);

        mockMvc.perform(get("/veiculos/buscar-por-placa")
                        .param("placa", "ABC1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.placa").value("ABC1234"))
                .andExpect(jsonPath("$.marca").value("Marca X"))
                .andExpect(jsonPath("$.modelo").value("Modelo A"))
                .andExpect(jsonPath("$.previousOwner").value("Owner1"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void listarVeiculosDisponiveisDeveRetornarStatus200() throws Exception {
        List<VeiculoDTO> veiculosDisponiveis = Arrays.asList(
                new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"))
        );
        when(veiculoService.listarTodosDisponiveis()).thenReturn(veiculosDisponiveis);

        mockMvc.perform(get("/veiculos/disponiveis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void criarVeiculoDeveRetornarStatus201() throws Exception {
        VeiculoDTO veiculoDTO = new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"));
        VeiculoDTO createdVeiculo = new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"));
        when(veiculoService.criar(any(VeiculoDTO.class))).thenReturn(createdVeiculo);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"placa\":\"ABC1234\", \"marca\":\"Marca X\", \"modelo\":\"Modelo A\", \"previousOwner\":\"Owner1\", \"disponivel\":true, \"valorDiariaReais\":100.00, \"valorDiariaDollar\":20.00}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.placa").value("ABC1234"))
                .andExpect(jsonPath("$.marca").value("Marca X"))
                .andExpect(jsonPath("$.modelo").value("Modelo A"))
                .andExpect(jsonPath("$.previousOwner").value("Owner1"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void updateVeiculoDeveRetornarStatus202() throws Exception {
        VeiculoDTO veiculoDTO = new VeiculoDTO("ABC1234", "Marca X", "Modelo A", "Owner1", true, new BigDecimal("100.00"), new BigDecimal("20.00"));
        when(veiculoService.update(any(VeiculoDTO.class))).thenReturn(veiculoDTO);

        mockMvc.perform(put("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"placa\":\"ABC1234\", \"marca\":\"Marca X\", \"modelo\":\"Modelo A\", \"previousOwner\":\"Owner1\", \"disponivel\":true, \"valorDiariaReais\":100.00, \"valorDiariaDollar\":20.00}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.placa").value("ABC1234"));
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    public void deleteVeiculoDeveRetornarStatus204() throws Exception {
        doNothing().when(veiculoService).delete("ABC1234");

        mockMvc.perform(delete("/veiculos/{placa}", "ABC1234")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

