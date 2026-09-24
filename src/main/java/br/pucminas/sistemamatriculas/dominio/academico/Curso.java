package br.pucminas.sistemamatriculas.dominio.academico;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Curso constituído por disciplinas.
 */
public class Curso implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String nome;
    private final int numeroCreditos;
    private final List<Disciplina> disciplinas = new ArrayList<>();

    public Curso(String nome, int numeroCreditos) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (numeroCreditos <= 0) {
            throw new IllegalArgumentException("O número de créditos deve ser positivo.");
        }
        this.nome = nome.trim();
        this.numeroCreditos = numeroCreditos;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        Objects.requireNonNull(disciplina, "A disciplina é obrigatória.");
        if (!disciplinas.contains(disciplina)) {
            disciplinas.add(disciplina);
        }
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroCreditos() {
        return numeroCreditos;
    }

    public List<Disciplina> getDisciplinas() {
        return Collections.unmodifiableList(disciplinas);
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        return outro instanceof Curso curso && nome.equalsIgnoreCase(curso.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return nome + " (" + numeroCreditos + " créditos)";
    }
}
