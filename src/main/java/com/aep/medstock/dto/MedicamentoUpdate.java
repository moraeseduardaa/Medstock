
package com.aep.medstock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class MedicamentoUpdate {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "quantidadeMinima é obrigatória")
    @PositiveOrZero(message = "Quantidade mínima deve ser positiva")
    private Integer quantidadeMinima;

}
