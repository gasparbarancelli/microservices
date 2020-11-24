package com.gasparbarancelli.vendas.repository;

import com.gasparbarancelli.vendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
}
