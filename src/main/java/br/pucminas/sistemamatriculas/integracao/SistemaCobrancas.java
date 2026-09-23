package br.pucminas.sistemamatriculas.integracao;

import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;

/**
 * Contrato abstrato com o Sistema de Cobranças externo.
 */
public interface SistemaCobrancas {
    void notificarInscricao(MatriculaSemestral matricula);
}

