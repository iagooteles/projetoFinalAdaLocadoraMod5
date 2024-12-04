package com.projetoFinalWeb.projetoWeb;

import com.projetoFinalWeb.projetoWeb.controller.VeiculoController;
import com.projetoFinalWeb.projetoWeb.dto.ResponsePagingDTO;
import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import com.projetoFinalWeb.projetoWeb.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VeiculoControllerTest {

    @InjectMocks
    private VeiculoController veiculoController;

    @Mock
    private VeiculoService veiculoService;

    private VeiculoDTO veiculoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        veiculoDTO = new VeiculoDTO();
        veiculoDTO.setPlaca("ZZZ222");
        veiculoDTO.setValorDiariaReais(BigDecimal.valueOf(100));
        veiculoDTO.setDisponivel(true);
    }

    @Test
    public void listarTodos_DeveRetornarListaDeVeiculos() {
        List<VeiculoDTO> veiculos = Arrays.asList(veiculoDTO);

        when(veiculoService.listarTodos()).thenReturn(veiculos);

        List<VeiculoDTO> response = veiculoController.listarTodos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("ZZZ222", response.get(0).getPlaca());
        assertEquals(BigDecimal.valueOf(100), response.get(0).getValorDiariaReais());
        verify(veiculoService, times(1)).listarTodos();
    }

    @Test
    public void listarTodosPaging_DeveReTornarPaginacaoDeVeiculos() {
        VeiculoDTO veiculo1 = new VeiculoDTO();
        veiculo1.setPlaca("ABC123");
        veiculo1.setValorDiariaReais(BigDecimal.valueOf(200));
        veiculo1.setDisponivel(true);

        List<VeiculoDTO> veiculos = Arrays.asList(veiculo1);

        ResponsePagingDTO<?> responsePagingDTO = ResponsePagingDTO.builder()
                .pageNumber(0)
                .list(Collections.singletonList(veiculos))
                .offset(10L)
                .build();

        when(veiculoService.listarTodosPaging(anyInt(), anyInt()))
                .thenReturn((ResponsePagingDTO) responsePagingDTO);

        ResponsePagingDTO<?> response = veiculoController.listarTodosPaging(1, 10);

        assertNotNull(response);
        assertEquals(0, response.getPageNumber());
        assertEquals(10, response.getOffset());
        assertNotNull(response.getList());
        assertEquals(1, ((List<?>) response.getList().get(0)).size());
        assertEquals("ABC123", ((VeiculoDTO) ((List<?>) response.getList().get(0)).get(0)).getPlaca());
        verify(veiculoService, times(1)).listarTodosPaging(1, 10);

    }

    @Test
    public void buscarPorPlaca_DeveRetornarVeiculoComPlacaCorreta() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("LOL7777");

        when(veiculoService.buscarPorPlaca("LOL7777")).thenReturn(veiculo);

        Veiculo response = veiculoController.buscarPorPlaca("LOL7777");

        assertNotNull(response);
        assertEquals("LOL7777", response.getPlaca());
        verify(veiculoService, times(1)).buscarPorPlaca("LOL7777");
    }

    @Test
    public void listarVeiculosDisponiveis_DeveRetornarListaDeVeiculosDisponiveis() {
        List<VeiculoDTO> veiculosDisponiveis = Collections.singletonList(veiculoDTO);

        when(veiculoService.listarTodosDisponiveis()).thenReturn(veiculosDisponiveis);

        List<VeiculoDTO> response = veiculoController.listarVeiculosDisponiveis();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.getFirst().getDisponivel());
        verify(veiculoService, times(1)).listarTodosDisponiveis();
    }

    @Test
    public void criar_DeveSalvarVeiculoComSucesso() {
        when(veiculoService.criar(veiculoDTO)).thenReturn(veiculoDTO);

        ResponseEntity<VeiculoDTO> response = veiculoController.criar(veiculoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("ZZZ222", response.getBody().getPlaca());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getValorDiariaReais());
        verify(veiculoService, times(1)).criar(veiculoDTO);
    }

    @Test
    public void update_DeveAtualizarVeiculoComSucesso() {
        VeiculoDTO veiculoAtualizado = new VeiculoDTO();
        veiculoAtualizado.setPlaca("LOL7777");
        veiculoAtualizado.setValorDiariaReais(BigDecimal.valueOf(120));
        veiculoAtualizado.setDisponivel(false);

        when(veiculoService.update(veiculoDTO)).thenReturn(veiculoAtualizado);

        ResponseEntity<VeiculoDTO> response = veiculoController.update(veiculoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(120), response.getBody().getValorDiariaReais());
        assertFalse(response.getBody().getDisponivel());
        verify(veiculoService, times(1)).update(veiculoDTO);
    }

    @Test
    public void delete_DeveDeletarVeiculoComSucesso() {
        doNothing().when(veiculoService).delete("ZZZ222");

        ResponseEntity<Void> response = veiculoController.delete("ZZZ222");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(veiculoService, times(1)).delete("ZZZ222");
    }

}
