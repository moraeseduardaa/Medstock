package com.aep.medstock.service;

import com.aep.medstock.dto.*;
import com.aep.medstock.exception.EstoqueException;
import com.aep.medstock.exception.MedicamentoException;
import com.aep.medstock.mapper.MedicamentoMapper;
import com.aep.medstock.model.LoteEstoque;
import com.aep.medstock.model.Medicamento;
import com.aep.medstock.model.MovimentacaoEstoque;
import com.aep.medstock.repository.MedicamentoRepository;
import com.aep.medstock.repository.MovimentacaoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository repository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;
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

        garantirLotes(medicamento);

        LoteEstoque loteEncontrado = null;

        for (LoteEstoque loteAtual : medicamento.getLotes()) {
            if (request.getLote().equals(loteAtual.getNumero())) {

                if (!request.getValidade().equals(loteAtual.getValidade())) {
                    throw new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.BAD_REQUEST,
                            "O lote informado já existe com uma validade diferente"
                    );
                }

                loteEncontrado = loteAtual;
                break;
            }
        }

        if (loteEncontrado == null) {
            loteEncontrado = new LoteEstoque();
            loteEncontrado.setNumero(request.getLote());
            loteEncontrado.setQuantidade(0);
            loteEncontrado.setValidade(request.getValidade());

            medicamento.getLotes().add(loteEncontrado);
        }

        loteEncontrado.setQuantidade(
                loteEncontrado.getQuantidade() + request.getQuantidade()
        );

        medicamento.setQuantidade(calcularQuantidadeTotal(medicamento));

        medicamento = repository.save(medicamento);

        registrarMovimentacao(medicamento, "ENTRADA", request.getQuantidade());

        return mapper.toResponse(medicamento);
    }

    public MedicamentoResponse registrarSaida(String id, SaidaEstoque request) {
        Medicamento medicamento = buscarOuLancarExcecao(id);

        if (request.getQuantidade() > medicamento.getQuantidade()) {
            throw new EstoqueException();
        }

        garantirLotes(medicamento);

        int quantidadeRestante = request.getQuantidade();

        List<LoteEstoque> lotesOrdenados = medicamento.getLotes()
                .stream()
                .filter(lote -> lote.getValidade() != null
                        && !lote.getValidade().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(LoteEstoque::getValidade))
                .toList();

        int quantidadeDisponivelTotal = lotesOrdenados.stream()
                .mapToInt(LoteEstoque::getQuantidade)
                .sum();

        if (request.getQuantidade() > quantidadeDisponivelTotal) {
            throw new EstoqueException();
        }

        for (LoteEstoque lote : lotesOrdenados) {
            if (quantidadeRestante == 0) {
                break;
            }

            int quantidadeDisponivel = lote.getQuantidade();
            int quantidadeRetirada = Math.min(
                    quantidadeDisponivel,
                    quantidadeRestante
            );

            lote.setQuantidade(quantidadeDisponivel - quantidadeRetirada);
            quantidadeRestante -= quantidadeRetirada;
        }

        if (quantidadeRestante > 0) {
            throw new EstoqueException();
        }

        medicamento.setQuantidade(calcularQuantidadeTotal(medicamento));
        medicamento = repository.save(medicamento);

        registrarMovimentacao(medicamento, "SAIDA", request.getQuantidade());

        return mapper.toResponse(medicamento);
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

    private void garantirLotes(Medicamento medicamento) {
        if (medicamento.getLotes() == null) {
            medicamento.setLotes(new ArrayList<>());
        }

        if (medicamento.getLotes().isEmpty()) {
            LoteEstoque lote = new LoteEstoque();
            lote.setNumero(medicamento.getLote());
            lote.setQuantidade(medicamento.getQuantidade());
            lote.setValidade(medicamento.getValidade());

            medicamento.getLotes().add(lote);
        }

        for (LoteEstoque lote : medicamento.getLotes()) {
            if (lote.getQuantidade() == null) {
                lote.setQuantidade(0);
            }
        }
    }

    private int calcularQuantidadeTotal(Medicamento medicamento) {
        return medicamento.getLotes()
                .stream()
                .mapToInt(LoteEstoque::getQuantidade)
                .sum();
    }

    private void registrarMovimentacao(
            Medicamento medicamento,
            String tipo,
            Integer quantidade
    ) {
        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setMedicamentoId(medicamento.getId());
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setData(LocalDateTime.now());

        movimentacaoRepository.save(movimentacao);
    }

    private Medicamento buscarOuLancarExcecao(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new MedicamentoException(id));
    }
}