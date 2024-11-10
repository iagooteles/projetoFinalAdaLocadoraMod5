package com.projetoFinalWeb.projetoWeb.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@ToString
public class VeiculoDTO {
    @Column(nullable = false)
    private String placa;
    @Column(nullable = false)
    private String marca;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private String previousOwner;
    @Column(nullable = false)
    private Boolean disponivel;
    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal valorDiariaReais;
    @Column(precision = 16, scale = 2)
    private BigDecimal valorDiariaDollar;
}
