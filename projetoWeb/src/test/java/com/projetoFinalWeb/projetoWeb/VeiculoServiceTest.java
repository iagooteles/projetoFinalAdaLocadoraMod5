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
import java.util.List;
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

    private Veiculo veiculo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        veiculo = new Veiculo();
        veiculo.setPlaca("ZZZ1234");
        veiculo.setMarca("Tesla");
        veiculo.setModelo("Model 3");
        veiculo.setDisponivel(true);
        veiculo.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo.setPreviousOwner("Stephen Curry");
    }

    @Test
    public void listarTodos_DeveRetornarListaDeVeiculos() {
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setPlaca("LLL1111");
        veiculo1.setMarca("Volkswagen");
        veiculo1.setModelo("Gol");
        veiculo1.setDisponivel(true);
        veiculo1.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo1.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo1.setPreviousOwner("La Melo Ball");

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setPlaca("XYZ1234");
        veiculo2.setMarca("Volkswagen");
        veiculo2.setModelo("Gol");
        veiculo2.setDisponivel(false);
        veiculo2.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo2.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo2.setPreviousOwner("Michael Jordan");

        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo1, veiculo2));

        List<VeiculoDTO> resultado = veiculoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("LLL1111", resultado.get(0).getPlaca());
        assertEquals("XYZ1234", resultado.get(1).getPlaca());
    }

    @Test
    public void buscarPorPlaca_DeveRetornarVeiculo() {
        when(veiculoRepository.findByPlaca("ZZZ1234")).thenReturn(Optional.of(veiculo));

        VeiculoDTO resultado = veiculoService.converterParaDTO(veiculo);

        assertNotNull(resultado);
        assertEquals("ZZZ1234", resultado.getPlaca());
        assertEquals("Tesla", resultado.getMarca());
        assertEquals("Model 3", resultado.getModelo());
        assertEquals("Stephen Curry", resultado.getPreviousOwner());
    }

    @Test
    public void listarTodosDisponiveis_DeveRetornarListaDeVeiculosDisponiveis() {
        Veiculo veiculo1 = new Veiculo();
        veiculo1.setPlaca("LLL1111");
        veiculo1.setMarca("Volkswagen");
        veiculo1.setModelo("Gol");
        veiculo1.setDisponivel(true);
        veiculo1.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo1.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo1.setPreviousOwner("La Melo Ball");

        Veiculo veiculo2 = new Veiculo();
        veiculo2.setPlaca("XYZ1234");
        veiculo2.setMarca("Volkswagen");
        veiculo2.setModelo("Gol");
        veiculo2.setDisponivel(false);
        veiculo2.setValorDiariaReais(BigDecimal.valueOf(100.0));
        veiculo2.setValorDiariaDollar(BigDecimal.valueOf(20.0));
        veiculo2.setPreviousOwner("Michael Jordan");

        when(veiculoRepository.findByDisponivelTrue()).thenReturn(List.of(veiculo1));

        List<VeiculoDTO> resultado = veiculoService.listarTodosDisponiveis();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("LLL1111", resultado.get(0).getPlaca());
        assertTrue(resultado.get(0).getDisponivel());

        verify(veiculoRepository, times(1)).findByDisponivelTrue();
    }

    @Test
    public void criarVeiculo_DeveCriarComSucesso() {
        when(nameService.getRandomName()).thenReturn("Lebron James");
        when(exchangeService.getConvertedPrice(veiculo.getValorDiariaReais())).thenReturn(BigDecimal.valueOf(20.0));
        veiculo.setPreviousOwner("Lebron James");

        VeiculoDTO resultado = veiculoService.converterParaDTO(veiculo);
        veiculoService.criar(resultado);
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));

        assertNotNull(resultado);
        assertEquals("Lebron James", resultado.getPreviousOwner());
        assertEquals(BigDecimal.valueOf(20.0), resultado.getValorDiariaDollar());
    }

    @Test
    public void update_DeveAtualizarVeiculoComSucesso() {
        when(veiculoRepository.findByPlaca("ZZZ1234")).thenReturn(Optional.of(veiculo));

        VeiculoDTO veiculoDTOAtualizado = new VeiculoDTO();
        veiculoDTOAtualizado.setPlaca("ZZZ1234");
        veiculoDTOAtualizado.setMarca("Tesla Atualizada");
        veiculoDTOAtualizado.setModelo("Model S");
        veiculoDTOAtualizado.setPreviousOwner("Elon Musk");
        veiculoDTOAtualizado.setDisponivel(false);
        veiculoDTOAtualizado.setValorDiariaReais(BigDecimal.valueOf(150.0));
        veiculoDTOAtualizado.setValorDiariaDollar(BigDecimal.valueOf(30.0));

        Veiculo veiculoAtualizado = new Veiculo();
        veiculoAtualizado.setPlaca(veiculoDTOAtualizado.getPlaca());
        veiculoAtualizado.setMarca(veiculoDTOAtualizado.getMarca());
        veiculoAtualizado.setModelo(veiculoDTOAtualizado.getModelo());
        veiculoAtualizado.setPreviousOwner(veiculoDTOAtualizado.getPreviousOwner());
        veiculoAtualizado.setDisponivel(veiculoDTOAtualizado.getDisponivel());
        veiculoAtualizado.setValorDiariaReais(veiculoDTOAtualizado.getValorDiariaReais());
        veiculoAtualizado.setValorDiariaDollar(veiculoDTOAtualizado.getValorDiariaDollar());

        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculoAtualizado);

        VeiculoDTO resultado = veiculoService.update(veiculoDTOAtualizado);
        VeiculoDTO veiculoConvertido = veiculoService.converterParaDTO(veiculoAtualizado);

        assertNotNull(resultado);
        assertEquals(veiculoConvertido.getPlaca(), resultado.getPlaca());
        assertEquals(veiculoConvertido.getMarca(), resultado.getMarca());
        assertEquals(veiculoConvertido.getModelo(), resultado.getModelo());
        assertEquals(veiculoConvertido.getPreviousOwner(), resultado.getPreviousOwner());
        assertEquals(veiculoConvertido.getDisponivel(), resultado.getDisponivel());
        assertEquals(veiculoConvertido.getValorDiariaReais(), resultado.getValorDiariaReais());
        assertEquals(veiculoConvertido.getValorDiariaDollar(), resultado.getValorDiariaDollar());

        verify(veiculoRepository, times(1)).findByPlaca("ZZZ1234");
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @Test
    public void delete_DeveExcluirComSucesso() {
        doNothing().when(veiculoRepository).deleteByPlaca("ZZZ1234");

        veiculoService.delete("ZZZ1234");

        verify(veiculoRepository, times(1)).deleteByPlaca("ZZZ1234");
    }
}
