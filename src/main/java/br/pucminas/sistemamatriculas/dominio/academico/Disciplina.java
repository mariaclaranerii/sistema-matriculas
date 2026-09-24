package br.pucminas.sistemamatriculas.dominio.academico;

import java.io.Serial;
import java.io.Serializable;

/**
 * Disciplina mantida pela Secretaria.
 */
public class Disciplina implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String nome;

    public Disciplina(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        return outro instanceof Disciplina disciplina
                && nome.equalsIgnoreCase(disciplina.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase(java.util.Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return nome;
    }
}
