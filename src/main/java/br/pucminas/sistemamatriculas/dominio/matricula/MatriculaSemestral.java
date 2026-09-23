package br.pucminas.sistemamatriculas.dominio.matricula;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupa as escolhas de um Aluno em um currículo semestral.
 */
public class MatriculaSemestral {
    private Aluno aluno;
    private CurriculoSemestral curriculo;
    private boolean confirmada;
    private List<MatriculaDisciplina> matriculas = new ArrayList<>();

    public MatriculaDisciplina adicionarDisciplina(
            OfertaDisciplina oferta,
            TipoOpcao tipo) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public boolean validarLimites() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void confirmar() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

