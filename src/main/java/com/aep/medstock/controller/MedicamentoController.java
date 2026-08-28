package com.aep.medstock.controller;

import com.aep.medstock.dto.*;
import com.aep.medstock.service.MedicamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final MedicamentoService service;

    @PostMapping
    public ResponseEntity<MedicamentoResponse> criar(@RequestBody @Valid MedicamentoCreate request) {
        MedicamentoResponse response = service.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoSummary>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> atualizar(
            @PathVariable
            String id,
            @RequestBody @Valid
            MedicamentoUpdate request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/entrada")
    public ResponseEntity<MedicamentoResponse> entrada(
            @PathVariable
            String id,
            @RequestBody @Valid
            EntradaEstoque request) {
        return ResponseEntity.ok(service.registrarEntrada(id, request));
    }

    @PatchMapping("/{id}/saida")
    public ResponseEntity<MedicamentoResponse> saida(
            @PathVariable
            String id,
            @RequestBody @Valid
            SaidaEstoque request) {

        return ResponseEntity.ok(service.registrarSaida(id, request));
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<MedicamentoSummary>> estoqueBaixo() {
        return ResponseEntity.ok(service.listarEstoqueBaixo());
    }

    @GetMapping("/vencimento-proximo")
    public ResponseEntity<List<MedicamentoSummary>> vencimentoProximo() {
        return ResponseEntity.ok(service.listarVencimentoProximo());

    }

}