package com.projetoFinalWeb.projetoWeb.service;

import com.projetoFinalWeb.projetoWeb.dto.ResponsePagingDTO;
import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import com.projetoFinalWeb.projetoWeb.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private NameService nameService;
    @Autowired
    private ExchangeService exchangeService;

    public List<VeiculoDTO> listarTodos() {
        return this.veiculoRepository
                .findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ResponsePagingDTO<?> listarTodosPaging(int pageNumber, int pageSize) {
        Page<Veiculo> page = this.veiculoRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "valorDiariaReais"));
        List<VeiculoDTO> list = page.stream().map(this::convert).toList();

        return ResponsePagingDTO.builder().pageNumber(page.getNumber()).list(Collections.singletonList(list))
                .offset(page.getPageable().getOffset()).build();
    }

    public Veiculo buscarPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa).orElse(null);
    }

    public List<VeiculoDTO> listarTodosDisponiveis() {
        return this.veiculoRepository
                .findByDisponivelTrue()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public VeiculoDTO criar(VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setMarca(veiculoDTO.getMarca());
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setDisponivel(veiculoDTO.getDisponivel());
        veiculo.setValorDiariaReais(veiculoDTO.getValorDiariaReais());

        if (veiculoDTO.getPreviousOwner() == null || veiculoDTO.getPreviousOwner().equalsIgnoreCase("")) {
            veiculo.setPreviousOwner(nameService.getRandomName());
            veiculoDTO.setPreviousOwner(veiculo.getPreviousOwner());
        } else {
            veiculo.setPreviousOwner(veiculoDTO.getPreviousOwner());
        }

        if (veiculoDTO.getValorDiariaDollar() == null) {

            veiculo.setValorDiariaDollar(exchangeService.getConvertedPrice(veiculo.getValorDiariaReais()));
            veiculoDTO.setValorDiariaDollar(veiculo.getValorDiariaDollar());
        } else {
            veiculo.setValorDiariaDollar(veiculoDTO.getValorDiariaDollar());
        }

        veiculoRepository.save(veiculo);
        return veiculoDTO;
    }

    public VeiculoDTO update(VeiculoDTO veiculoDTO) {
        Optional<Veiculo> optionalVeiculo = Optional.ofNullable(this.buscarPorPlaca(veiculoDTO.getPlaca()));

        if (optionalVeiculo.isPresent()) {
            Veiculo veiculo = optionalVeiculo.get();

            veiculo.setPlaca(veiculoDTO.getPlaca());
            veiculo.setMarca(veiculoDTO.getMarca());
            veiculo.setModelo(veiculoDTO.getModelo());
            veiculo.setPreviousOwner(veiculoDTO.getPreviousOwner());
            veiculo.setDisponivel(veiculoDTO.getDisponivel());
            veiculo.setValorDiariaReais(veiculoDTO.getValorDiariaReais());
            veiculo.setValorDiariaDollar(veiculoDTO.getValorDiariaDollar());

            this.veiculoRepository.save(veiculo);
            return veiculoDTO;
        }
        return null;
    }

    @Transactional
    public void delete(String placa) {
        this.veiculoRepository.deleteByPlaca(placa);
    }

    public VeiculoDTO converterParaDTO(Veiculo veiculo) {
        return convert(veiculo);
    }

    private VeiculoDTO convert (Veiculo veiculo) {
        VeiculoDTO veiculoDTO = new VeiculoDTO();

        veiculoDTO.setPlaca(veiculo.getPlaca());
        veiculoDTO.setMarca(veiculo.getMarca());
        veiculoDTO.setModelo(veiculo.getModelo());
        veiculoDTO.setPreviousOwner(veiculo.getPreviousOwner());
        veiculoDTO.setValorDiariaReais(veiculo.getValorDiariaReais());
        veiculoDTO.setValorDiariaDollar(veiculo.getValorDiariaDollar());
        veiculoDTO.setDisponivel(veiculo.getDisponivel());

        return veiculoDTO;
    }

}
