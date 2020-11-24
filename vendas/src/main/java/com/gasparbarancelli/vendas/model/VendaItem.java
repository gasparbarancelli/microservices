package com.gasparbarancelli.vendas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "VENDA_ITEM")
public class VendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_VENDA")
    private Venda venda;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PRODUTO")
    private Produto produto;

    @Column(name = "QUANTIDADE", nullable = false, scale = 10, precision = 2)
    private BigDecimal quantidade;

    @Column(name = "VALOR", nullable = false, scale = 10, precision = 2)
    private BigDecimal valor;

    @Column(name = "VALOR_TOTAL", nullable = false, scale = 15, precision = 2)
    private BigDecimal valorTotal;

    @Deprecated
    public VendaItem() {
    }

    public VendaItem(
            @NonNull Produto produto,
            @NonNull BigDecimal quantidade,
            @NonNull BigDecimal valor) {
        this.produto = Objects.requireNonNull(produto, "produto não pode ser nulo");
        this.quantidade = Objects.requireNonNull(quantidade, "quantidade não pode ser nulo");
        this.valor = Objects.requireNonNull(valor, "valor não pode ser nulo");
        this.valorTotal = quantidade.multiply(valor);
    }

    public void setVenda(Venda venda) {
        this.venda = Objects.requireNonNull(venda, "venda não pode ser nula");
    }

    public Long getId() {
        return id;
    }

    public Venda getVenda() {
        return venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendaItem vendaItem = (VendaItem) o;
        return Objects.equals(venda, vendaItem.venda) && Objects.equals(produto, vendaItem.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(venda, produto);
    }
}
