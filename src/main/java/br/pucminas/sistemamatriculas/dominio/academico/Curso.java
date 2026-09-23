package br.pucminas.sistemamatriculas.dominio.academico;

import java.util.ArrayList;
import java.util.List;

/**
 * Curso constituído por disciplinas.
 */
public class Curso {
    private String nome;
    private int numeroCreditos;
    private List<Disciplina> disciplinas = new ArrayList<>();
}

