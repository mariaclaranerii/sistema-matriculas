package br.pucminas.sistemamatriculas.dominio.matricula;

import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;

/**
 * Escolha de uma disciplina dentro da matrícula semestral.
 */
public class MatriculaDisciplina {
    private OfertaDisciplina oferta;
    private TipoOpcao tipoOpcao;
    private StatusMatricula status;

    public void cancelar() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

