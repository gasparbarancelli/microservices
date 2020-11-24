package com.gasparbarancelli.vendas.repository;

import com.gasparbarancelli.vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
