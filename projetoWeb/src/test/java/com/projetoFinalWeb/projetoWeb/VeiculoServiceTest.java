package com.projetoFinalWeb.projetoWeb;

import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import com.projetoFinalWeb.projetoWeb.repository.VeiculoRepository;
import com.projetoFinalWeb.projetoWeb.service.VeiculoService;
import com.projetoFinalWeb.projetoWeb.service.NameService;
import com.projetoFinalWeb.projetoWeb.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private NameService nameService;

    @Mock
    private ExchangeService exchangeService;

    private VeiculoDTO veiculoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        veiculoDTO = new VeiculoDTO();
        veiculoDTO.setPlaca("ABC1234");
        veiculoDTO.setMarca("Fiat");
        veiculoDTO.setModelo("Palio");
        veiculoDTO.setDisponivel(true);
        veiculoDTO.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculoDTO.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculoDTO.setPreviousOwner("Carlos Souza");
    }

    @Test
    public void criarVeiculo_DeveCriarComSucesso() {
        when(nameService.getRandomName()).thenReturn("Carlos Souza");
        when(exchangeService.getConvertedPrice(veiculoDTO.getValorDiariaReais())).thenReturn(BigDecimal.valueOf(20.0));

        VeiculoDTO resultado = veiculoService.criar(veiculoDTO);

        assertNotNull(resultado);
        assertEquals("Carlos Souza", resultado.getPreviousOwner());
        assertEquals(BigDecimal.valueOf(20.0), resultado.getValorDiariaDollar());
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @Test
    public void buscarPorPlaca_DeveRetornarVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setMarca("Fiat");
        veiculo.setModelo("Palio");
        veiculo.setDisponivel(true);
        veiculo.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo.setPreviousOwner("João Silva");

        when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(veiculo));

        Veiculo resultado = veiculoService.buscarPorPlaca("ABC1234");

        assertNotNull(resultado);
        assertEquals("Fiat", resultado.getMarca());
        assertEquals("Palio", resultado.getModelo());
    }

    @Test
    public void excluirVeiculo_DeveExcluirComSucesso() {
        doNothing().when(veiculoRepository).deleteByPlaca("ABC1234");

        veiculoService.delete("ABC1234");

        verify(veiculoRepository, times(1)).deleteByPlaca("ABC1234");
    }
}
