package br.pucminas.sistemamatriculas.aplicacao;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.acesso.Professor;
import br.pucminas.sistemamatriculas.acesso.Secretaria;
import br.pucminas.sistemamatriculas.dominio.academico.Curso;
import br.pucminas.sistemamatriculas.dominio.academico.Disciplina;
import br.pucminas.sistemamatriculas.persistencia.EstadoSistema;

import java.util.List;

/**
 * Cria um conjunto pequeno de dados apenas na primeira execução.
 */
public final class DadosIniciais {
    private DadosIniciais() {
    }

    public static void inicializar(EstadoSistema estado, SistemaMatriculas sistema) {
        if (!estado.isNovo()) {
            return;
        }

        estado.adicionarUsuario(new Secretaria("secretaria", "123"));
        sistema.manterProfessor(new Professor("professor", "123"));
        sistema.manterAluno(new Aluno("aluno", "123"));

        List<Disciplina> disciplinas = List.of(
                new Disciplina("Projeto de Software"),
                new Disciplina("Engenharia de Requisitos"),
                new Disciplina("Banco de Dados"),
                new Disciplina("Algoritmos"),
                new Disciplina("Padrões de Projeto"),
                new Disciplina("Testes de Software"));
        disciplinas.forEach(sistema::manterDisciplina);

        Curso curso = new Curso("Engenharia de Software", 240);
        disciplinas.forEach(curso::adicionarDisciplina);
        estado.adicionarCurso(curso);
        sistema.gerarCurriculo("2026/2", List.of(curso));
    }
}
