package br.pucminas.sistemamatriculas.dominio.academico;

import java.util.ArrayList;
import java.util.List;

/**
 * Currículo gerado para um semestre.
 */
public class CurriculoSemestral {
    private String identificadorSemestre;
    private List<Curso> cursos = new ArrayList<>();
    private PeriodoMatricula periodoMatricula;
    private List<OfertaDisciplina> ofertas = new ArrayList<>();

    public OfertaDisciplina adicionarOferta(Disciplina disciplina) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public List<OfertaDisciplina> listarOfertas() {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

