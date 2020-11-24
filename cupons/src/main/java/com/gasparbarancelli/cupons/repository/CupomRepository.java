package com.gasparbarancelli.cupons.repository;

import com.gasparbarancelli.cupons.model.Cupom;
import org.springframework.data.repository.CrudRepository;

public interface CupomRepository extends CrudRepository<Cupom, String> {
}
