package com.gasparbarancelli.cupons.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

@RedisHash(value = "cupom", timeToLive = 60 * 60)
public class Cupom {

    @Id
    private String id;

    private BigDecimal desconto;

    @Deprecated
    public Cupom() {
    }

    public Cupom(@NonNull BigDecimal desconto) {
        this.desconto = Objects.requireNonNull(desconto, "desconto não pode ser nulo");
        this.id = RandomStringUtils.randomAlphanumeric(5);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cupom cupom = (Cupom) o;
        return Objects.equals(id, cupom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cupom{" +
                "id=" + id +
                ", desconto=" + desconto +
                '}';
    }
}
