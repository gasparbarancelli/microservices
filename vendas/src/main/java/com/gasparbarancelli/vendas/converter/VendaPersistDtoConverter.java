package com.gasparbarancelli.vendas.converter;

import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.exception.ProdutoNotFoundException;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.model.VendaItem;
import com.gasparbarancelli.vendas.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VendaPersistDtoConverter {

    private final ProdutoRepository produtoRepository;

    public VendaPersistDtoConverter(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Venda toVenda(VendaPersistDto vendaPersist) {
        var itens = toVendaItem(vendaPersist);
        return new Venda(itens);
    }

    public Set<VendaItem> toVendaItem(VendaPersistDto vendaPersist) {
        return vendaPersist.getItens().stream()
                .map(it -> {
                    var produto = produtoRepository.findById(it.getProduto())
                            .orElseThrow(() -> new ProdutoNotFoundException(it.getProduto()));
                    return new VendaItem(produto, it.getQuantidade(), it.getValor());
                })
                .collect(Collectors.toSet());
    }

}
