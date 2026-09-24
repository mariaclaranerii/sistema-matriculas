package br.pucminas.sistemamatriculas.dominio.matricula;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Agrupa as escolhas de um Aluno em um currículo semestral.
 */
public class MatriculaSemestral implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Aluno aluno;
    private final CurriculoSemestral curriculo;
    private boolean confirmada;
    private final List<MatriculaDisciplina> matriculas = new ArrayList<>();

    public MatriculaSemestral(Aluno aluno, CurriculoSemestral curriculo) {
        this.aluno = Objects.requireNonNull(aluno, "O aluno é obrigatório.");
        this.curriculo = Objects.requireNonNull(curriculo, "O currículo é obrigatório.");
    }

    public MatriculaDisciplina adicionarDisciplina(
            OfertaDisciplina oferta,
            TipoOpcao tipo) {
        if (confirmada) {
            throw new IllegalStateException("A matrícula já foi confirmada.");
        }
        Objects.requireNonNull(oferta, "A oferta é obrigatória.");
        Objects.requireNonNull(tipo, "O tipo de opção é obrigatório.");
        if (!curriculo.listarOfertas().contains(oferta)) {
            throw new IllegalArgumentException("A oferta não pertence ao currículo selecionado.");
        }
        boolean repetida = matriculas.stream()
                .anyMatch(item -> item.getOferta().equals(oferta));
        if (repetida) {
            throw new IllegalArgumentException("A disciplina foi selecionada mais de uma vez.");
        }
        MatriculaDisciplina matricula = new MatriculaDisciplina(oferta, tipo);
        matriculas.add(matricula);
        return matricula;
    }

    public boolean validarLimites() {
        long primeirasOpcoes = matriculas.stream()
                .filter(item -> item.getTipoOpcao() == TipoOpcao.PRIMEIRA_OPCAO)
                .count();
        long alternativas = matriculas.stream()
                .filter(item -> item.getTipoOpcao() == TipoOpcao.ALTERNATIVA)
                .count();
        return !matriculas.isEmpty() && primeirasOpcoes <= 4 && alternativas <= 2;
    }

    public void confirmar() {
        if (confirmada) {
            throw new IllegalStateException("A matrícula já foi confirmada.");
        }
        if (!validarLimites()) {
            throw new IllegalArgumentException(
                    "Selecione de 1 a 4 primeiras opções e no máximo 2 alternativas.");
        }
        boolean algumaOfertaIndisponivel = matriculas.stream()
                .map(MatriculaDisciplina::getOferta)
                .anyMatch(oferta -> !oferta.podeReceberMatricula());
        if (algumaOfertaIndisponivel) {
            throw new IllegalStateException("Uma das ofertas selecionadas não aceita matrícula.");
        }
        matriculas.forEach(item -> item.getOferta().registrarMatricula(item));
        confirmada = true;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public CurriculoSemestral getCurriculo() {
        return curriculo;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public List<MatriculaDisciplina> getMatriculas() {
        return Collections.unmodifiableList(matriculas);
    }

    public List<MatriculaDisciplina> getMatriculasAtivas() {
        return matriculas.stream()
                .filter(item -> item.getStatus() == StatusMatricula.ATIVA)
                .toList();
    }

    @Override
    public String toString() {
        return aluno.getLogin() + " - " + curriculo.getIdentificadorSemestre();
    }
}
