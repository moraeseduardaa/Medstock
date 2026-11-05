
package com.aep.medstock.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoteEstoque {

    private String numero;
    private Integer quantidade;
    private LocalDate validade;
}
