package com.projetoFinalWeb.projetoWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "veiculos")
@NoArgsConstructor
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
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

    public Veiculo(String placa, String marca, String modelo, String previousOwner, Boolean disponivel, BigDecimal valorDiariaReais, BigDecimal valorDiariaDollar) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.previousOwner = previousOwner;
        this.disponivel = disponivel;
        this.valorDiariaReais = valorDiariaReais;
        this.valorDiariaDollar = valorDiariaDollar;
    }
}
