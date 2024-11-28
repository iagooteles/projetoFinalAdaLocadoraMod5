package com.projetoFinalWeb.projetoWeb;

import com.projetoFinalWeb.projetoWeb.controller.VeiculoController;
import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
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
    public void criar_DeveSalvarVeiculoComSucesso() {
        when(veiculoService.criar(veiculoDTO)).thenReturn(veiculoDTO);

        ResponseEntity<VeiculoDTO> response = veiculoController.criar(veiculoDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("ZZZ222", response.getBody().getPlaca());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getValorDiariaReais());
        verify(veiculoService, times(1)).criar(veiculoDTO);
    }
}
