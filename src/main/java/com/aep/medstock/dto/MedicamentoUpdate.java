package com.aep.medstock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicamentoUpdate {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "Quantidade deve ser positiva")
    private Integer quantidade;

    @NotNull(message = "quantidadeMinima é obrigatória")
    @PositiveOrZero(message = "QuantidadeMinima deve ser positiva")
    private Integer quantidadeMinima;

    @NotNull(message = "Validade é obrigatória")
    private LocalDate validade;

    @NotBlank(message = "Lote é obrigatório")
    private String lote;

}
