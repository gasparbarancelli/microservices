package com.gasparbarancelli.vendas.converter;

import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.exception.ProdutoNotFoundException;
import com.gasparbarancelli.vendas.external.CupomExternalApi;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.model.VendaItem;
import com.gasparbarancelli.vendas.repository.ProdutoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VendaPersistDtoConverter {

    private final ProdutoRepository produtoRepository;

    private final CupomExternalApi cupomExternalApi;

    public VendaPersistDtoConverter(ProdutoRepository produtoRepository, CupomExternalApi cupomExternalApi) {
        this.produtoRepository = produtoRepository;
        this.cupomExternalApi = cupomExternalApi;
    }

    public Venda toVenda(VendaPersistDto vendaPersist) {
        var itens = toVendaItem(vendaPersist);
        var desconto = getDesconto(vendaPersist);
        return new Venda(itens, desconto);
    }

    private BigDecimal getDesconto(VendaPersistDto vendaPersist) {
        if (vendaPersist.getCupom().isPresent() && StringUtils.isNotBlank(vendaPersist.getCupom().get())) {
            return cupomExternalApi.getDescontoByCupom(vendaPersist.getCupom().get());
        }
        return BigDecimal.ZERO;
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
