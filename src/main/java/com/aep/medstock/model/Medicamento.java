package com.aep.medstock.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "medicamentos")
public class Medicamento {

    @Id
    private String id;
    private String nome;
    private Integer quantidade;
    private Integer quantidadeMinima;
    private LocalDate validade;
    private String lote;
    private LocalDate dataEntrada;

}