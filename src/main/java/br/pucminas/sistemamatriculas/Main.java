package br.pucminas.sistemamatriculas;

import br.pucminas.sistemamatriculas.aplicacao.DadosIniciais;
import br.pucminas.sistemamatriculas.aplicacao.SistemaMatriculas;
import br.pucminas.sistemamatriculas.integracao.SistemaCobrancasArquivo;
import br.pucminas.sistemamatriculas.persistencia.EstadoSistema;
import br.pucminas.sistemamatriculas.persistencia.PersistenciaArquivo;
import br.pucminas.sistemamatriculas.ui.InterfaceConsole;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Ponto de entrada do protótipo executável.
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Path diretorioDados = args.length > 0 ? Path.of(args[0]) : Path.of("dados");
        PersistenciaArquivo persistencia = new PersistenciaArquivo(
                diretorioDados.resolve("sistema-matriculas.dat"));
        try {
            EstadoSistema estado = persistencia.carregar();
            SistemaMatriculas sistema = new SistemaMatriculas(
                    estado,
                    new SistemaCobrancasArquivo(
                            diretorioDados.resolve("notificacoes-cobranca.txt")));
            DadosIniciais.inicializar(estado, sistema);
            persistencia.salvar(estado);
            new InterfaceConsole(sistema, persistencia, new Scanner(System.in)).executar();
        } catch (IOException excecao) {
            System.err.println("Não foi possível iniciar o sistema: " + excecao.getMessage());
        }
    }
}
