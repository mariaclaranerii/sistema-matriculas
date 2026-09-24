package br.pucminas.sistemamatriculas.dominio.academico;

import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.StatusMatricula;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Ocorrência semestral de uma disciplina.
 */
public class OfertaDisciplina implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int MINIMO_ALUNOS = 3;
    public static final int MAXIMO_ALUNOS = 60;

    private final Disciplina disciplina;
    private SituacaoOferta situacao = SituacaoOferta.PENDENTE;
    private boolean inscricoesAbertas = true;
    private final List<MatriculaDisciplina> matriculas = new ArrayList<>();

    public OfertaDisciplina(Disciplina disciplina) {
        this.disciplina = Objects.requireNonNull(disciplina, "A disciplina é obrigatória.");
    }

    public int quantidadeMatriculados() {
        return (int) matriculas.stream()
                .filter(matricula -> matricula.getStatus() == StatusMatricula.ATIVA)
                .count();
    }

    public boolean podeReceberMatricula() {
        return inscricoesAbertas
                && situacao == SituacaoOferta.PENDENTE
                && quantidadeMatriculados() < MAXIMO_ALUNOS;
    }

    public void registrarMatricula(MatriculaDisciplina matricula) {
        Objects.requireNonNull(matricula, "A matrícula é obrigatória.");
        if (!this.equals(matricula.getOferta())) {
            throw new IllegalArgumentException("A matrícula pertence a outra oferta.");
        }
        if (!podeReceberMatricula()) {
            throw new IllegalStateException("A oferta não aceita novas matrículas.");
        }
        if (!matriculas.contains(matricula)) {
            matriculas.add(matricula);
        }
        if (quantidadeMatriculados() >= MAXIMO_ALUNOS) {
            inscricoesAbertas = false;
        }
    }

    public void cancelarMatricula(MatriculaDisciplina matricula) {
        Objects.requireNonNull(matricula, "A matrícula é obrigatória.");
        if (!matriculas.contains(matricula)) {
            throw new IllegalArgumentException("A matrícula não pertence a esta oferta.");
        }
        matricula.cancelar();
        if (situacao == SituacaoOferta.PENDENTE
                && quantidadeMatriculados() < MAXIMO_ALUNOS) {
            inscricoesAbertas = true;
        }
    }

    public void processarResultado() {
        inscricoesAbertas = false;
        situacao = quantidadeMatriculados() >= MINIMO_ALUNOS
                ? SituacaoOferta.ATIVA
                : SituacaoOferta.CANCELADA;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public SituacaoOferta getSituacao() {
        return situacao;
    }

    public boolean isInscricoesAbertas() {
        return inscricoesAbertas;
    }

    public List<MatriculaDisciplina> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    @Override
    public String toString() {
        return disciplina.getNome() + " [" + quantidadeMatriculados() + "/"
                + MAXIMO_ALUNOS + ", " + situacao + "]";
    }
}
