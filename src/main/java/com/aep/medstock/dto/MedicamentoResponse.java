package com.aep.medstock.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicamentoResponse {

    private String id;
    private String nome;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private LocalDate validade;
    private String lote;
    private LocalDate dataEntrada;
}