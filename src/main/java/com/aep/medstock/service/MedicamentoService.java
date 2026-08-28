package com.aep.medstock.service;

import com.aep.medstock.dto.*;
import com.aep.medstock.exception.EstoqueException;
import com.aep.medstock.exception.MedicamentoException;
import com.aep.medstock.mapper.MedicamentoMapper;
import com.aep.medstock.model.Medicamento;
import com.aep.medstock.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository repository;
    private final MedicamentoMapper mapper;


    public MedicamentoResponse criar(MedicamentoCreate request) {
        Medicamento medicamento = mapper.toModel(request);
        return mapper.toResponse(repository.save(medicamento));
    }


    public List<MedicamentoSummary> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    public MedicamentoResponse buscarPorId(String id) {
        return mapper.toResponse(buscarOuLancarExcecao(id));
    }

    public MedicamentoResponse atualizar(String id, MedicamentoUpdate request) {
        Medicamento medicamento = buscarOuLancarExcecao(id);
        mapper.updateModel(medicamento, request);
        return mapper.toResponse(repository.save(medicamento));
    }

    public void deletar(String id) {
        buscarOuLancarExcecao(id);
        repository.deleteById(id);
    }

    public MedicamentoResponse registrarEntrada(String id, EntradaEstoque request) {
        Medicamento medicamento = buscarOuLancarExcecao(id);
        medicamento.setQuantidade(medicamento.getQuantidade() + request.getQuantidade());
        return mapper.toResponse(repository.save(medicamento));
    }

    public MedicamentoResponse registrarSaida(String id, SaidaEstoque request) {
        Medicamento medicamento = buscarOuLancarExcecao(id);
        if (request.getQuantidade() > medicamento.getQuantidade()) {
            throw new EstoqueException();
        }
        medicamento.setQuantidade(medicamento.getQuantidade() - request.getQuantidade());
        return mapper.toResponse(repository.save(medicamento));
    }

    public List<MedicamentoSummary> listarEstoqueBaixo() {
        return repository.findAll()
                .stream()
                .filter(m -> m.getQuantidade() < m.getQuantidadeMinima())
                .map(mapper::toSummaryResponse)
                .toList();
    }

    public List<MedicamentoSummary> listarVencimentoProximo() {
        LocalDate limite = LocalDate.now().plusDays(30);
        return repository.findByValidadeLessThanEqual(limite)
                .stream()
                .map(mapper::toSummaryResponse)
                .toList();
    }

    private Medicamento buscarOuLancarExcecao(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicamentoException(id));
    }


}