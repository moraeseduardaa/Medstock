package com.aep.medstock.controller;

import com.aep.medstock.dto.*;
import com.aep.medstock.service.MedicamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentoControllerTest {

    @Mock
    private MedicamentoService service;

    @InjectMocks
    private MedicamentoController controller;

    @Test
    void deveListarTodos() {
        List<MedicamentoSummary> medicamentos = List.of(
                new MedicamentoSummary()
        );

        when(service.listarTodos()).thenReturn(medicamentos);

        ResponseEntity<List<MedicamentoSummary>> response =
                controller.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentos, response.getBody());

        verify(service).listarTodos();
    }

    @Test
    void deveBuscarPorId() {
        MedicamentoResponse medicamento = new MedicamentoResponse();
        medicamento.setId("123");
        medicamento.setNome("Paracetamol");

        when(service.buscarPorId("123")).thenReturn(medicamento);

        ResponseEntity<MedicamentoResponse> response =
                controller.buscarPorId("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamento, response.getBody());

        verify(service).buscarPorId("123");
    }

    @Test
    void deveAtualizar() {
        MedicamentoUpdate request = new MedicamentoUpdate();

        MedicamentoResponse medicamento = new MedicamentoResponse();
        medicamento.setId("123");
        medicamento.setNome("Paracetamol");

        when(service.atualizar("123", request)).thenReturn(medicamento);

        ResponseEntity<MedicamentoResponse> response =
                controller.atualizar("123", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamento, response.getBody());

        verify(service).atualizar("123", request);
    }

    @Test
    void deveDeletar() {
        doNothing().when(service).deletar("123");

        ResponseEntity<Void> response =
                controller.deletar("123");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(service).deletar("123");
    }

    @Test
    void deveRegistrarEntrada() {
        EntradaEstoque request = new EntradaEstoque();

        MedicamentoResponse medicamento = new MedicamentoResponse();
        medicamento.setId("123");
        medicamento.setNome("Paracetamol");

        when(service.registrarEntrada("123", request))
                .thenReturn(medicamento);

        ResponseEntity<MedicamentoResponse> response =
                controller.entrada("123", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamento, response.getBody());

        verify(service).registrarEntrada("123", request);
    }

    @Test
    void deveRegistrarSaida() {
        SaidaEstoque request = new SaidaEstoque();

        MedicamentoResponse medicamento = new MedicamentoResponse();
        medicamento.setId("123");
        medicamento.setNome("Paracetamol");

        when(service.registrarSaida("123", request))
                .thenReturn(medicamento);

        ResponseEntity<MedicamentoResponse> response =
                controller.saida("123", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamento, response.getBody());

        verify(service).registrarSaida("123", request);
    }

    @Test
    void deveListarEstoqueBaixo() {
        List<MedicamentoSummary> medicamentos = List.of(
                new MedicamentoSummary()
        );

        when(service.listarEstoqueBaixo()).thenReturn(medicamentos);

        ResponseEntity<List<MedicamentoSummary>> response =
                controller.estoqueBaixo();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentos, response.getBody());

        verify(service).listarEstoqueBaixo();
    }

    @Test
    void deveListarVencimentoProximo() {
        List<MedicamentoSummary> medicamentos = List.of(
                new MedicamentoSummary()
        );

        when(service.listarVencimentoProximo()).thenReturn(medicamentos);

        ResponseEntity<List<MedicamentoSummary>> response =
                controller.vencimentoProximo();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicamentos, response.getBody());

        verify(service).listarVencimentoProximo();
    }
}