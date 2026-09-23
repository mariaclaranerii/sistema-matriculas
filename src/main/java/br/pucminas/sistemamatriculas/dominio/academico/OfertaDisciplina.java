package br.pucminas.sistemamatriculas.dominio.academico;

import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;

import java.util.ArrayList;
import java.util.List;

/**
 * Ocorrência semestral de uma disciplina.
 */
public class OfertaDisciplina {
    private static final int MINIMO_ALUNOS = 3;
    private static final int MAXIMO_ALUNOS = 60;

    private Disciplina disciplina;
    private SituacaoOferta situacao;
    private boolean inscricoesAbertas;
    private List<MatriculaDisciplina> matriculas = new ArrayList<>();

    public int quantidadeMatriculados() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public boolean podeReceberMatricula() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void registrarMatricula(MatriculaDisciplina matricula) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void cancelarMatricula(MatriculaDisciplina matricula) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void processarResultado() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

