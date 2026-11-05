
package com.aep.medstock.repository;

import com.aep.medstock.model.MovimentacaoEstoque;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovimentacaoEstoqueRepository extends MongoRepository<MovimentacaoEstoque, String> {
}
