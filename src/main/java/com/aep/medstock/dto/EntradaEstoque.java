package com.aep.medstock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EntradaEstoque {

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;

    @NotBlank(message = "Número do lote é obrigatório")
    private String lote;

    @NotNull(message = "Validade é obrigatória")
    private LocalDate validade;
}