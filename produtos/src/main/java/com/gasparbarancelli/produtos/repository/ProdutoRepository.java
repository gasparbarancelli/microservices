package com.gasparbarancelli.produtos.repository;

import com.gasparbarancelli.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
