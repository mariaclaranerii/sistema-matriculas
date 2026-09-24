package br.pucminas.sistemamatriculas.dominio;

import java.io.Serial;

/**
 * Indica que uma operação não atende às regras do Sistema de Matrículas.
 */
public class RegraNegocioException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
