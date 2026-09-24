package br.pucminas.sistemamatriculas.dominio.academico;

import java.io.Serial;
import java.io.Serializable;

/**
 * Condição temporal utilizada para matrícula e cancelamento.
 */
public class PeriodoMatricula implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private StatusPeriodo status;

    public PeriodoMatricula() {
        this.status = StatusPeriodo.EM_ANDAMENTO;
    }

    public boolean estaEmAndamento() {
        return status == StatusPeriodo.EM_ANDAMENTO;
    }

    public void encerrar() {
        status = StatusPeriodo.ENCERRADO;
    }

    public StatusPeriodo getStatus() {
        return status;
    }
}
