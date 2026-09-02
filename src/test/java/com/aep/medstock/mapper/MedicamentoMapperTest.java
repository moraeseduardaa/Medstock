
package com.aep.medstock.mapper;

import com.aep.medstock.dto.MedicamentoCreate;
import com.aep.medstock.dto.MedicamentoResponse;
import com.aep.medstock.dto.MedicamentoSummary;
import com.aep.medstock.dto.MedicamentoUpdate;
import com.aep.medstock.model.Medicamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicamentoMapperTest {

    private final MedicamentoMapper mapper = new MedicamentoMapper();

    @Test
    void deveConverterCreateParaModel() {

        MedicamentoCreate request = new MedicamentoCreate();
        request.setNome("Paracetamol");
        request.setQuantidade(100);
        request.setQuantidadeMinima(20);
        request.setValidade(LocalDate.of(2027, 12, 31));
        request.setLote("LOT-001");

        Medicamento medicamento = mapper.toModel(request);

        assertNotNull(medicamento);
        assertEquals("Paracetamol", medicamento.getNome());
        assertEquals(100, medicamento.getQuantidade());
        assertEquals(20, medicamento.getQuantidadeMinima());
        assertEquals(
                LocalDate.of(2027, 12, 31),
                medicamento.getValidade()
        );
        assertEquals("LOT-001", medicamento.getLote());
        assertEquals(LocalDate.now(), medicamento.getDataEntrada());
    }

    @Test
    void deveAtualizarModel() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);
        medicamento.setQuantidadeMinima(20);
        medicamento.setValidade(LocalDate.of(2027, 12, 31));
        medicamento.setLote("LOT-001");

        MedicamentoUpdate request = new MedicamentoUpdate();
        request.setNome("Paracetamol 500mg");
        request.setQuantidade(200);
        request.setQuantidadeMinima(30);
        request.setValidade(LocalDate.of(2028, 12, 31));
        request.setLote("LOT-002");

        mapper.updateModel(medicamento, request);

        assertEquals("Paracetamol 500mg", medicamento.getNome());
        assertEquals(200, medicamento.getQuantidade());
        assertEquals(30, medicamento.getQuantidadeMinima());
        assertEquals(
                LocalDate.of(2028, 12, 31),
                medicamento.getValidade()
        );
        assertEquals("LOT-002", medicamento.getLote());

        // O ID não deve ser alterado pelo mapper
        assertEquals("1", medicamento.getId());
    }

    @Test
    void deveConverterParaResponse() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);
        medicamento.setQuantidadeMinima(20);
        medicamento.setValidade(LocalDate.of(2027, 12, 31));
        medicamento.setLote("LOT-001");
        medicamento.setDataEntrada(LocalDate.of(2026, 8, 29));

        MedicamentoResponse response = mapper.toResponse(medicamento);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("Paracetamol", response.getNome());
        assertEquals(100, response.getQuantidade());
        assertEquals(20, response.getQuantidadeMinima());
        assertEquals(
                LocalDate.of(2027, 12, 31),
                response.getValidade()
        );
        assertEquals("LOT-001", response.getLote());
        assertEquals(
                LocalDate.of(2026, 8, 29),
                response.getDataEntrada()
        );
    }

    @Test
    void deveConverterParaSummaryResponse() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);
        medicamento.setQuantidadeMinima(20);
        medicamento.setValidade(LocalDate.of(2027, 12, 31));
        medicamento.setLote("LOT-001");

        MedicamentoSummary response =
                mapper.toSummaryResponse(medicamento);

        assertNotNull(response);
        assertEquals("1", response.getId());
        assertEquals("Paracetamol", response.getNome());
        assertEquals(100, response.getQuantidade());
        assertEquals(20, response.getQuantidadeMinima());
        assertEquals(
                LocalDate.of(2027, 12, 31),
                response.getValidade()
        );
    }
}

