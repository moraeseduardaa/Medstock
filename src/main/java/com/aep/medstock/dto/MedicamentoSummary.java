package com.aep.medstock.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicamentoSummary {

    private String id;
    private String nome;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private LocalDate validade;

}
