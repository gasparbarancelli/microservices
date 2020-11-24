package com.gasparbarancelli.vendas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VENDA")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA", nullable = false)
    private LocalDateTime data;

    @Column(name = "VALOR", nullable = false, scale = 15, precision = 2)
    private BigDecimal valor;

    @JsonManagedReference
    @OneToMany(mappedBy = "venda", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<VendaItem> itens = new HashSet<>();

    @Deprecated
    public Venda() {
    }

    public Venda(@NonNull Set<VendaItem> itens) {
        setItens(itens);
        this.data = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setItens(@NonNull Set<VendaItem> itens) {
        Objects.requireNonNull(itens, "lista de itens não pode ser nula");
        itens.forEach(it -> it.setVenda(this));

        if (!this.itens.isEmpty()) {
            this.itens.clear();
        }
        this.itens.addAll(itens);


        this.valor = itens.stream()
                .map(VendaItem::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<VendaItem> getItens() {
        return Collections.unmodifiableSet(itens);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
