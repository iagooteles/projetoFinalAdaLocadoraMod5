package com.projetoFinalWeb.projetoWeb.controller;

import com.projetoFinalWeb.projetoWeb.dto.ResponsePagingDTO;
import com.projetoFinalWeb.projetoWeb.dto.VeiculoDTO;
import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import com.projetoFinalWeb.projetoWeb.service.VeiculoService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping(value = "listAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VeiculoDTO> listarTodos() {
        return this.veiculoService.listarTodos();
    }

    @GetMapping(value = "all-paging", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponsePagingDTO<?> listarTodosPaging(@RequestParam("pageNumber") int pageNumber,
                                              @RequestParam("pageSize") int pageSize) {
        return this.veiculoService.listarTodosPaging(pageNumber, pageSize);
    }

    @GetMapping(value = "buscar-por-placa", produces = MediaType.APPLICATION_JSON_VALUE)
    public Veiculo buscarPorPlaca(@RequestParam("placa") String placa) {
        return this.veiculoService.buscarPorPlaca(placa);
    }

    @GetMapping("/disponiveis")
    public List<VeiculoDTO> listarVeiculosDisponiveis() {
        return veiculoService.listarTodosDisponiveis();
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> criar(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.criar(veiculoDTO));
    }

    @PutMapping
    public ResponseEntity<VeiculoDTO> update(@RequestBody VeiculoDTO veiculoDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.veiculoService.update(veiculoDTO));
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> delete(@PathVariable("placa") String placa) {
        veiculoService.delete(placa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
