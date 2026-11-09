
package com.aep.medstock.mapper;

import com.aep.medstock.model.LoteEstoque;
import java.util.List;
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
    void deveAtualizarModelSemAlterarEstoqueELotes() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);
        medicamento.setQuantidadeMinima(20);
        medicamento.setValidade(LocalDate.of(2027, 12, 31));
        medicamento.setLote("LOT-001");

        LoteEstoque lote = new LoteEstoque();
        lote.setNumero("LOT-001");
        lote.setQuantidade(100);
        lote.setValidade(LocalDate.of(2027, 12, 31));

        medicamento.setLotes(List.of(lote));

        MedicamentoUpdate request = new MedicamentoUpdate();
        request.setNome("Paracetamol 500mg");
        request.setQuantidadeMinima(30);

        mapper.updateModel(medicamento, request);

        assertEquals("Paracetamol 500mg", medicamento.getNome());
        assertEquals(100, medicamento.getQuantidade());
        assertEquals(30, medicamento.getQuantidadeMinima());

        assertEquals(
                LocalDate.of(2027, 12, 31),
                medicamento.getValidade()
        );

        assertEquals("LOT-001", medicamento.getLote());

        assertEquals(1, medicamento.getLotes().size());
        assertEquals("LOT-001", medicamento.getLotes().get(0).getNumero());
        assertEquals(100, medicamento.getLotes().get(0).getQuantidade());

        assertEquals(
                LocalDate.of(2027, 12, 31),
                medicamento.getLotes().get(0).getValidade()
        );

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