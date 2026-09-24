package br.pucminas.sistemamatriculas.aplicacao;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.acesso.Professor;
import br.pucminas.sistemamatriculas.acesso.Usuario;
import br.pucminas.sistemamatriculas.dominio.RegraNegocioException;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.Curso;
import br.pucminas.sistemamatriculas.dominio.academico.Disciplina;
import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;
import br.pucminas.sistemamatriculas.dominio.matricula.StatusMatricula;
import br.pucminas.sistemamatriculas.dominio.matricula.TipoOpcao;
import br.pucminas.sistemamatriculas.integracao.SistemaCobrancas;
import br.pucminas.sistemamatriculas.persistencia.EstadoSistema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Coordena as operações previstas nos casos de uso do sistema.
 */
public class SistemaMatriculas {
    private final EstadoSistema estado;
    private final SistemaCobrancas sistemaCobrancas;

    public SistemaMatriculas(EstadoSistema estado, SistemaCobrancas sistemaCobrancas) {
        this.estado = Objects.requireNonNull(estado, "O estado do sistema é obrigatório.");
        this.sistemaCobrancas = Objects.requireNonNull(
                sistemaCobrancas, "O Sistema de Cobranças é obrigatório.");
    }

    public Usuario autenticar(String login, String senha) {
        Usuario usuario;
        try {
            usuario = estado.localizarUsuario(login).orElse(null);
        } catch (IllegalArgumentException excecao) {
            usuario = null;
        }
        if (usuario == null || !usuario.validarSenha(senha)) {
            throw new RegraNegocioException("Login ou senha inválidos.");
        }
        return usuario;
    }

    public CurriculoSemestral gerarCurriculo(
            String identificadorSemestre,
            List<Curso> cursos) {
        if (cursos == null || cursos.isEmpty()) {
            throw new RegraNegocioException("Selecione ao menos um curso.");
        }
        CurriculoSemestral curriculo = new CurriculoSemestral(
                identificadorSemestre, cursos);
        cursos.stream()
                .flatMap(curso -> curso.getDisciplinas().stream())
                .distinct()
                .forEach(curriculo::adicionarOferta);
        if (curriculo.listarOfertas().isEmpty()) {
            throw new RegraNegocioException(
                    "Não é possível gerar um currículo sem disciplinas.");
        }
        estado.adicionarCurriculo(curriculo);
        return curriculo;
    }

    public void manterDisciplina(Disciplina disciplina) {
        estado.adicionarDisciplina(Objects.requireNonNull(disciplina));
    }

    public void manterProfessor(Professor professor) {
        estado.adicionarUsuario(Objects.requireNonNull(professor));
    }

    public void manterAluno(Aluno aluno) {
        estado.adicionarUsuario(Objects.requireNonNull(aluno));
    }

    public MatriculaSemestral realizarMatricula(
            Aluno aluno,
            CurriculoSemestral curriculo,
            List<OfertaDisciplina> primeirasOpcoes,
            List<OfertaDisciplina> alternativas) {
        Objects.requireNonNull(aluno, "O aluno é obrigatório.");
        Objects.requireNonNull(curriculo, "O currículo é obrigatório.");
        if (!curriculo.getPeriodoMatricula().estaEmAndamento()) {
            throw new RegraNegocioException("O período de matrículas está encerrado.");
        }
        if (estado.localizarMatricula(aluno, curriculo).isPresent()) {
            throw new RegraNegocioException(
                    "O aluno já possui matrícula nesse currículo semestral.");
        }

        List<OfertaDisciplina> primeiras = listaSegura(primeirasOpcoes);
        List<OfertaDisciplina> opcoesAlternativas = listaSegura(alternativas);
        if (primeiras.size() > 4 || opcoesAlternativas.size() > 2) {
            throw new RegraNegocioException(
                    "São permitidas até 4 primeiras opções e até 2 alternativas.");
        }
        List<OfertaDisciplina> todas = new ArrayList<>(primeiras);
        todas.addAll(opcoesAlternativas);
        if (todas.isEmpty()) {
            throw new RegraNegocioException("Selecione ao menos uma disciplina.");
        }
        if (todas.stream().distinct().count() != todas.size()) {
            throw new RegraNegocioException(
                    "A mesma disciplina não pode ser selecionada mais de uma vez.");
        }

        MatriculaSemestral matricula = new MatriculaSemestral(aluno, curriculo);
        primeiras.forEach(oferta ->
                matricula.adicionarDisciplina(oferta, TipoOpcao.PRIMEIRA_OPCAO));
        opcoesAlternativas.forEach(oferta ->
                matricula.adicionarDisciplina(oferta, TipoOpcao.ALTERNATIVA));
        matricula.confirmar();
        estado.adicionarMatricula(matricula);
        sistemaCobrancas.notificarInscricao(matricula);
        return matricula;
    }

    public void cancelarMatricula(MatriculaDisciplina matricula) {
        Objects.requireNonNull(matricula, "A matrícula é obrigatória.");
        MatriculaSemestral matriculaSemestral = estado.localizarMatricula(matricula)
                .orElseThrow(() -> new RegraNegocioException(
                        "A matrícula informada não foi encontrada."));
        if (!matriculaSemestral.getCurriculo().getPeriodoMatricula().estaEmAndamento()) {
            throw new RegraNegocioException(
                    "O cancelamento só é permitido durante o período de matrículas.");
        }
        if (matricula.getStatus() == StatusMatricula.CANCELADA) {
            throw new RegraNegocioException("A matrícula já está cancelada.");
        }
        matricula.getOferta().cancelarMatricula(matricula);
    }

    public void processarEncerramento(CurriculoSemestral curriculo) {
        Objects.requireNonNull(curriculo, "O currículo é obrigatório.");
        if (!curriculo.getPeriodoMatricula().estaEmAndamento()) {
            throw new RegraNegocioException("O período já foi encerrado.");
        }
        curriculo.getPeriodoMatricula().encerrar();
        curriculo.listarOfertas().forEach(OfertaDisciplina::processarResultado);
    }

    public List<Aluno> consultarAlunos(OfertaDisciplina oferta) {
        Objects.requireNonNull(oferta, "A oferta é obrigatória.");
        return estado.getMatriculas().stream()
                .filter(MatriculaSemestral::isConfirmada)
                .filter(matricula -> matricula.getMatriculas().stream()
                        .anyMatch(item -> item.getOferta().equals(oferta)
                                && item.getStatus() == StatusMatricula.ATIVA))
                .map(MatriculaSemestral::getAluno)
                .distinct()
                .toList();
    }

    public List<MatriculaSemestral> consultarMatriculas(Aluno aluno) {
        return estado.getMatriculas().stream()
                .filter(matricula -> matricula.getAluno().equals(aluno))
                .toList();
    }

    public EstadoSistema getEstado() {
        return estado;
    }

    private static <T> List<T> listaSegura(List<T> lista) {
        return lista == null ? Collections.emptyList() : List.copyOf(lista);
    }
}
