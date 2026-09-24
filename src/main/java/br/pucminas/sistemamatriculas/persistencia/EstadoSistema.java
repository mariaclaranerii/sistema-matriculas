package br.pucminas.sistemamatriculas.persistencia;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.acesso.Professor;
import br.pucminas.sistemamatriculas.acesso.Usuario;
import br.pucminas.sistemamatriculas.dominio.RegraNegocioException;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.Curso;
import br.pucminas.sistemamatriculas.dominio.academico.Disciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Raiz do conjunto de objetos persistidos em arquivo.
 */
public class EstadoSistema implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Disciplina> disciplinas = new ArrayList<>();
    private final List<Curso> cursos = new ArrayList<>();
    private final List<CurriculoSemestral> curriculos = new ArrayList<>();
    private final List<MatriculaSemestral> matriculas = new ArrayList<>();

    public void adicionarUsuario(Usuario usuario) {
        if (localizarUsuario(usuario.getLogin()).isPresent()) {
            throw new RegraNegocioException("Já existe um usuário com esse login.");
        }
        usuarios.add(usuario);
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        if (disciplinas.contains(disciplina)) {
            throw new RegraNegocioException("A disciplina já está cadastrada.");
        }
        disciplinas.add(disciplina);
    }

    public void adicionarCurso(Curso curso) {
        if (cursos.contains(curso)) {
            throw new RegraNegocioException("O curso já está cadastrado.");
        }
        cursos.add(curso);
    }

    public void adicionarCurriculo(CurriculoSemestral curriculo) {
        if (curriculos.contains(curriculo)) {
            throw new RegraNegocioException("Já existe um currículo para esse semestre.");
        }
        curriculos.add(curriculo);
    }

    public void adicionarMatricula(MatriculaSemestral matricula) {
        boolean existente = matriculas.stream().anyMatch(item ->
                item.getAluno().equals(matricula.getAluno())
                        && item.getCurriculo().equals(matricula.getCurriculo()));
        if (existente) {
            throw new RegraNegocioException(
                    "O aluno já possui matrícula nesse currículo semestral.");
        }
        matriculas.add(matricula);
    }

    public Optional<Usuario> localizarUsuario(String login) {
        String loginNormalizado = Usuario.normalizarLogin(login);
        return usuarios.stream()
                .filter(usuario -> usuario.getLogin().equals(loginNormalizado))
                .findFirst();
    }

    public Optional<CurriculoSemestral> localizarCurriculo(String identificador) {
        return curriculos.stream()
                .filter(curriculo -> curriculo.getIdentificadorSemestre()
                        .equalsIgnoreCase(identificador.trim()))
                .findFirst();
    }

    public Optional<MatriculaSemestral> localizarMatricula(
            Aluno aluno,
            CurriculoSemestral curriculo) {
        return matriculas.stream()
                .filter(item -> item.getAluno().equals(aluno)
                        && item.getCurriculo().equals(curriculo))
                .findFirst();
    }

    public Optional<MatriculaSemestral> localizarMatricula(
            MatriculaDisciplina matriculaDisciplina) {
        return matriculas.stream()
                .filter(item -> item.getMatriculas().contains(matriculaDisciplina))
                .findFirst();
    }

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public List<Aluno> getAlunos() {
        return usuarios.stream()
                .filter(Aluno.class::isInstance)
                .map(Aluno.class::cast)
                .toList();
    }

    public List<Professor> getProfessores() {
        return usuarios.stream()
                .filter(Professor.class::isInstance)
                .map(Professor.class::cast)
                .toList();
    }

    public List<Disciplina> getDisciplinas() {
        return Collections.unmodifiableList(disciplinas);
    }

    public List<Curso> getCursos() {
        return Collections.unmodifiableList(cursos);
    }

    public List<CurriculoSemestral> getCurriculos() {
        return Collections.unmodifiableList(curriculos);
    }

    public List<MatriculaSemestral> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public boolean isNovo() {
        return usuarios.isEmpty() && disciplinas.isEmpty() && cursos.isEmpty()
                && curriculos.isEmpty() && matriculas.isEmpty();
    }
}
