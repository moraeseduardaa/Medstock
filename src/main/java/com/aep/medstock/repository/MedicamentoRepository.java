package com.aep.medstock.repository;

import com.aep.medstock.model.Medicamento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicamentoRepository extends MongoRepository<Medicamento, String> {

    List<Medicamento> findByValidadeLessThanEqual(LocalDate data);

}