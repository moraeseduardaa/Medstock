
package com.aep.medstock.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "movimentacoes")
public class MovimentacaoEstoque {

    @Id
    private String id;

    private String medicamentoId;
    private String tipo;
    private Integer quantidade;
    private LocalDateTime data;
}
