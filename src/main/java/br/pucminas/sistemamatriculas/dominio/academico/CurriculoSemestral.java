package br.pucminas.sistemamatriculas.dominio.academico;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Currículo gerado para um semestre.
 */
public class CurriculoSemestral implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String identificadorSemestre;
    private final List<Curso> cursos = new ArrayList<>();
    private final PeriodoMatricula periodoMatricula = new PeriodoMatricula();
    private final List<OfertaDisciplina> ofertas = new ArrayList<>();

    public CurriculoSemestral(String identificadorSemestre, List<Curso> cursos) {
        if (identificadorSemestre == null || identificadorSemestre.isBlank()) {
            throw new IllegalArgumentException("O identificador do semestre é obrigatório.");
        }
        if (cursos == null || cursos.isEmpty()) {
            throw new IllegalArgumentException("O currículo deve possuir ao menos um curso.");
        }
        this.identificadorSemestre = identificadorSemestre.trim();
        cursos.forEach(this::adicionarCurso);
    }

    public void adicionarCurso(Curso curso) {
        Objects.requireNonNull(curso, "O curso é obrigatório.");
        if (!cursos.contains(curso)) {
            cursos.add(curso);
        }
    }

    public OfertaDisciplina adicionarOferta(Disciplina disciplina) {
        Objects.requireNonNull(disciplina, "A disciplina é obrigatória.");
        return ofertas.stream()
                .filter(oferta -> oferta.getDisciplina().equals(disciplina))
                .findFirst()
                .orElseGet(() -> {
                    OfertaDisciplina oferta = new OfertaDisciplina(disciplina);
                    ofertas.add(oferta);
                    return oferta;
                });
    }

    public List<OfertaDisciplina> listarOfertas() {
        return Collections.unmodifiableList(ofertas);
    }

    public String getIdentificadorSemestre() {
        return identificadorSemestre;
    }

    public List<Curso> getCursos() {
        return Collections.unmodifiableList(cursos);
    }

    public PeriodoMatricula getPeriodoMatricula() {
        return periodoMatricula;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        return outro instanceof CurriculoSemestral curriculo
                && identificadorSemestre.equalsIgnoreCase(curriculo.identificadorSemestre);
    }

    @Override
    public int hashCode() {
        return identificadorSemestre.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return identificadorSemestre + " - " + periodoMatricula.getStatus();
    }
}
