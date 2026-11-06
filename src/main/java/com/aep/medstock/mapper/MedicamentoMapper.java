
package com.aep.medstock.mapper;

import com.aep.medstock.dto.*;
import com.aep.medstock.model.LoteEstoque;
import com.aep.medstock.model.Medicamento;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MedicamentoMapper {

    public Medicamento toModel(MedicamentoCreate request) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNome(request.getNome());
        medicamento.setQuantidade(request.getQuantidade());
        medicamento.setQuantidadeMinima(request.getQuantidadeMinima());
        medicamento.setValidade(request.getValidade());
        medicamento.setLote(request.getLote());
        medicamento.setDataEntrada(LocalDate.now());

        LoteEstoque lote = new LoteEstoque();
        lote.setNumero(request.getLote());
        lote.setQuantidade(request.getQuantidade());
        lote.setValidade(request.getValidade());

        medicamento.setLotes(new ArrayList<>(List.of(lote)));

        return medicamento;
    }

    public void updateModel(Medicamento medicamento, MedicamentoUpdate request) {
        medicamento.setNome(request.getNome());
        medicamento.setQuantidadeMinima(request.getQuantidadeMinima());
    }

    public MedicamentoResponse toResponse(Medicamento medicamento) {
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(medicamento.getId());
        response.setNome(medicamento.getNome());
        response.setQuantidade(medicamento.getQuantidade());
        response.setQuantidadeMinima(medicamento.getQuantidadeMinima());
        response.setValidade(medicamento.getValidade());
        response.setLote(medicamento.getLote());
        response.setDataEntrada(medicamento.getDataEntrada());
        return response;
    }

    public MedicamentoSummary toSummaryResponse(Medicamento medicamento) {
        MedicamentoSummary response = new MedicamentoSummary();
        response.setId(medicamento.getId());
        response.setNome(medicamento.getNome());
        response.setQuantidade(medicamento.getQuantidade());
        response.setQuantidadeMinima(medicamento.getQuantidadeMinima());
        response.setValidade(medicamento.getValidade());
        return response;
    }
}
