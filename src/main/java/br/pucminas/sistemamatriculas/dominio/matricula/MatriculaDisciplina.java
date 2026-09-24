package br.pucminas.sistemamatriculas.dominio.matricula;

import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Escolha de uma disciplina dentro da matrícula semestral.
 */
public class MatriculaDisciplina implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final OfertaDisciplina oferta;
    private final TipoOpcao tipoOpcao;
    private StatusMatricula status = StatusMatricula.ATIVA;

    public MatriculaDisciplina(OfertaDisciplina oferta, TipoOpcao tipoOpcao) {
        this.oferta = Objects.requireNonNull(oferta, "A oferta é obrigatória.");
        this.tipoOpcao = Objects.requireNonNull(tipoOpcao, "O tipo de opção é obrigatório.");
    }

    public void cancelar() {
        status = StatusMatricula.CANCELADA;
    }

    public OfertaDisciplina getOferta() {
        return oferta;
    }

    public TipoOpcao getTipoOpcao() {
        return tipoOpcao;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return oferta.getDisciplina().getNome() + " - " + tipoOpcao + " - " + status;
    }
}
