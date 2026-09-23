package br.pucminas.sistemamatriculas.aplicacao;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.acesso.Professor;
import br.pucminas.sistemamatriculas.acesso.Usuario;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.Curso;
import br.pucminas.sistemamatriculas.dominio.academico.Disciplina;
import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;
import br.pucminas.sistemamatriculas.integracao.SistemaCobrancas;

import java.util.List;

/**
 * Controlador das operações previstas no diagrama de classes.
 */
public class SistemaMatriculas {
    private SistemaCobrancas sistemaCobrancas;

    public Usuario autenticar(String login, String senha) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public CurriculoSemestral gerarCurriculo(
            String identificadorSemestre,
            List<Curso> cursos) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void manterDisciplina(Disciplina disciplina) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void manterProfessor(Professor professor) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void manterAluno(Aluno aluno) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public MatriculaSemestral realizarMatricula(
            Aluno aluno,
            CurriculoSemestral curriculo,
            List<OfertaDisciplina> primeirasOpcoes,
            List<OfertaDisciplina> alternativas) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void cancelarMatricula(MatriculaDisciplina matricula) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public void processarEncerramento(CurriculoSemestral curriculo) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }

    public List<Aluno> consultarAlunos(OfertaDisciplina oferta) {
        throw new UnsupportedOperationException("Stub da Sprint 2.");
    }
}

