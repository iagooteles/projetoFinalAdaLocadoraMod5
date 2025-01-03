package com.projetoFinalWeb.projetoWeb.repository;

import com.projetoFinalWeb.projetoWeb.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByPlaca(String placa);

    void deleteByPlaca(String placa);

    List<Veiculo> findByDisponivelTrue();
}
