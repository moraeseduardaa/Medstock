package com.aep.medstock.service;

import com.aep.medstock.dto.*;
import com.aep.medstock.exception.EstoqueException;
import com.aep.medstock.exception.MedicamentoException;
import com.aep.medstock.mapper.MedicamentoMapper;
import com.aep.medstock.model.Medicamento;
import com.aep.medstock.repository.MedicamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoServiceTest {

    @Mock
    private MedicamentoRepository repository;

    @Mock
    private MedicamentoMapper mapper;

    @InjectMocks
    private MedicamentoService service;


    @Test
    void deveCriarMedicamento() {

        MedicamentoCreate request = new MedicamentoCreate();
        request.setNome("Paracetamol");
        request.setQuantidade(100);
        request.setQuantidadeMinima(20);
        request.setValidade(LocalDate.of(2027, 12, 31));
        request.setLote("LOT-001");

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);
        medicamento.setQuantidadeMinima(20);
        medicamento.setValidade(LocalDate.of(2027, 12, 31));
        medicamento.setLote("LOT-001");

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId("1");
        response.setNome("Paracetamol");

        when(mapper.toModel(request)).thenReturn(medicamento);
        when(repository.save(medicamento)).thenReturn(medicamento);
        when(mapper.toResponse(medicamento)).thenReturn(response);

        MedicamentoResponse resultado = service.criar(request);

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Paracetamol", resultado.getNome());

        verify(mapper).toModel(request);
        verify(repository).save(medicamento);
        verify(mapper).toResponse(medicamento);
    }


    @Test
    void deveBuscarMedicamentoPorId() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId("1");
        response.setNome("Paracetamol");

        when(repository.findById("1")).thenReturn(Optional.of(medicamento));
        when(mapper.toResponse(medicamento)).thenReturn(response);

        MedicamentoResponse resultado = service.buscarPorId("1");

        assertNotNull(resultado);
        assertEquals("1", resultado.getId());
        assertEquals("Paracetamol", resultado.getNome());

        verify(repository).findById("1");
        verify(mapper).toResponse(medicamento);
    }


    @Test
    void deveLancarExcecaoQuandoMedicamentoNaoExiste() {

        when(repository.findById("999")).thenReturn(Optional.empty());

        assertThrows(
                MedicamentoException.class,
                () -> service.buscarPorId("999")
        );

        verify(repository).findById("999");
    }


    @Test
    void deveRegistrarEntradaNoEstoque() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);

        EntradaEstoque request = new EntradaEstoque();
        request.setQuantidade(50);

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId("1");
        response.setNome("Paracetamol");
        response.setQuantidade(150);

        when(repository.findById("1")).thenReturn(Optional.of(medicamento));
        when(repository.save(medicamento)).thenReturn(medicamento);
        when(mapper.toResponse(medicamento)).thenReturn(response);

        MedicamentoResponse resultado =
                service.registrarEntrada("1", request);

        assertEquals(150, medicamento.getQuantidade());
        assertEquals(150, resultado.getQuantidade());

        verify(repository).findById("1");
        verify(repository).save(medicamento);
        verify(mapper).toResponse(medicamento);
    }


    @Test
    void deveRegistrarSaidaNoEstoque() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);

        SaidaEstoque request = new SaidaEstoque();
        request.setQuantidade(30);

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId("1");
        response.setNome("Paracetamol");
        response.setQuantidade(70);

        when(repository.findById("1")).thenReturn(Optional.of(medicamento));
        when(repository.save(medicamento)).thenReturn(medicamento);
        when(mapper.toResponse(medicamento)).thenReturn(response);

        MedicamentoResponse resultado =
                service.registrarSaida("1", request);

        assertEquals(70, medicamento.getQuantidade());
        assertEquals(70, resultado.getQuantidade());

        verify(repository).findById("1");
        verify(repository).save(medicamento);
        verify(mapper).toResponse(medicamento);
    }


    @Test
    void deveLancarExcecaoQuandoSaidaMaiorQueEstoque() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setQuantidade(20);

        SaidaEstoque request = new SaidaEstoque();
        request.setQuantidade(50);

        when(repository.findById("1")).thenReturn(Optional.of(medicamento));

        assertThrows(
                EstoqueException.class,
                () -> service.registrarSaida("1", request)
        );

        assertEquals(20, medicamento.getQuantidade());

        verify(repository).findById("1");
        verify(repository, never()).save(any());
    }


    @Test
    void deveAtualizarMedicamento() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setQuantidade(100);

        MedicamentoUpdate request = new MedicamentoUpdate();
        request.setNome("Paracetamol 500mg");
        request.setQuantidade(200);
        request.setQuantidadeMinima(30);
        request.setValidade(LocalDate.of(2028, 12, 31));
        request.setLote("LOT-002");

        MedicamentoResponse response = new MedicamentoResponse();
        response.setId("1");
        response.setNome("Paracetamol 500mg");
        response.setQuantidade(200);

        when(repository.findById("1"))
                .thenReturn(Optional.of(medicamento));

        when(repository.save(medicamento))
                .thenReturn(medicamento);

        when(mapper.toResponse(medicamento))
                .thenReturn(response);


        doAnswer(invocation -> {

            Medicamento med = invocation.getArgument(0);
            MedicamentoUpdate req = invocation.getArgument(1);

            med.setNome(req.getNome());
            med.setQuantidade(req.getQuantidade());
            med.setQuantidadeMinima(req.getQuantidadeMinima());
            med.setValidade(req.getValidade());
            med.setLote(req.getLote());

            return null;

        }).when(mapper).updateModel(medicamento, request);

        MedicamentoResponse resultado =
                service.atualizar("1", request);

        assertEquals(
                "Paracetamol 500mg",
                medicamento.getNome()
        );

        assertEquals(
                200,
                medicamento.getQuantidade()
        );

        assertEquals(
                "Paracetamol 500mg",
                resultado.getNome()
        );

        assertEquals(
                200,
                resultado.getQuantidade()
        );

        verify(repository).findById("1");
        verify(repository).save(medicamento);
        verify(mapper).updateModel(medicamento, request);
        verify(mapper).toResponse(medicamento);
    }


    @Test
    void deveDeletarMedicamento() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");

        when(repository.findById("1"))
                .thenReturn(Optional.of(medicamento));

        service.deletar("1");

        verify(repository).findById("1");
        verify(repository).deleteById("1");
    }


    @Test
    void deveListarEstoqueBaixo() {

        Medicamento medicamento1 = new Medicamento();
        medicamento1.setId("1");
        medicamento1.setNome("Paracetamol");
        medicamento1.setQuantidade(10);
        medicamento1.setQuantidadeMinima(20);

        Medicamento medicamento2 = new Medicamento();
        medicamento2.setId("2");
        medicamento2.setNome("Dipirona");
        medicamento2.setQuantidade(100);
        medicamento2.setQuantidadeMinima(20);

        MedicamentoSummary summary = new MedicamentoSummary();
        summary.setId("1");
        summary.setNome("Paracetamol");

        when(repository.findAll())
                .thenReturn(List.of(medicamento1, medicamento2));

        when(mapper.toSummaryResponse(medicamento1))
                .thenReturn(summary);

        List<MedicamentoSummary> resultado =
                service.listarEstoqueBaixo();

        assertEquals(1, resultado.size());
        assertEquals(
                "Paracetamol",
                resultado.get(0).getNome()
        );

        verify(repository).findAll();
        verify(mapper).toSummaryResponse(medicamento1);
    }


    @Test
    void deveListarVencimentoProximo() {

        Medicamento medicamento = new Medicamento();
        medicamento.setId("1");
        medicamento.setNome("Paracetamol");
        medicamento.setValidade(
                LocalDate.now().plusDays(10)
        );

        MedicamentoSummary summary = new MedicamentoSummary();
        summary.setId("1");
        summary.setNome("Paracetamol");

        when(repository.findByValidadeLessThanEqual(any(LocalDate.class)))
                .thenReturn(List.of(medicamento));

        when(mapper.toSummaryResponse(medicamento))
                .thenReturn(summary);

        List<MedicamentoSummary> resultado =
                service.listarVencimentoProximo();

        assertEquals(1, resultado.size());

        assertEquals(
                "Paracetamol",
                resultado.get(0).getNome()
        );

        verify(repository)
                .findByValidadeLessThanEqual(
                        any(LocalDate.class)
                );

        verify(mapper)
                .toSummaryResponse(medicamento);
    }
}