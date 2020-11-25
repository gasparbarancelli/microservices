package com.gasparbarancelli.email.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Document("EMAIL_VENDA")
public class Email {

    @Id
    private String id;
    private Long fk;
    private String email;
    private LocalDateTime data;
    private Rotina rotina;
    private String titulo;
    private String conteudo;

    @Deprecated
    public Email() {
    }

    public Email(
            @NonNull Long fk,
            @NonNull String email,
            @NonNull Rotina rotina,
            @NonNull String titulo,
            @NonNull String conteudo) {
        this.fk = Objects.requireNonNull(fk, "fk não pode ser nulo");
        this.email = Objects.requireNonNull(email, "email não pode ser nulo");
        this.rotina = Objects.requireNonNull(rotina, "rotina não pode ser nula");
        this.titulo = Objects.requireNonNull(titulo, "titulo não pode ser nulo");
        this.conteudo = Objects.requireNonNull(conteudo, "conteudo não pode ser nulo");
        this.data = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Long getFk() {
        return fk;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(id, email.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
