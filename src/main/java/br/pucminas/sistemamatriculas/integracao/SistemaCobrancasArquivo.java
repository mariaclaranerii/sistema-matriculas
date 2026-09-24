package br.pucminas.sistemamatriculas.integracao;

import br.pucminas.sistemamatriculas.dominio.RegraNegocioException;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Simula o sistema externo registrando cada notificação em um arquivo de texto.
 */
public class SistemaCobrancasArquivo implements SistemaCobrancas {
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Path arquivo;

    public SistemaCobrancasArquivo(Path arquivo) {
        this.arquivo = arquivo.toAbsolutePath().normalize();
    }

    @Override
    public synchronized void notificarInscricao(MatriculaSemestral matricula) {
        try {
            Path diretorio = arquivo.getParent();
            if (diretorio != null) {
                Files.createDirectories(diretorio);
            }
            String disciplinas = matricula.getMatriculas().stream()
                    .map(MatriculaDisciplina::getOferta)
                    .map(oferta -> oferta.getDisciplina().getNome())
                    .map(SistemaCobrancasArquivo::sanitizar)
                    .collect(Collectors.joining(";"));
            String linha = String.join("|",
                    LocalDateTime.now().format(FORMATO_DATA),
                    sanitizar(matricula.getAluno().getLogin()),
                    sanitizar(matricula.getCurriculo().getIdentificadorSemestre()),
                    disciplinas) + System.lineSeparator();
            Files.writeString(arquivo, linha, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException excecao) {
            throw new RegraNegocioException(
                    "Não foi possível notificar o Sistema de Cobranças.", excecao);
        }
    }

    private static String sanitizar(String valor) {
        return valor.replace('|', '/').replace('\n', ' ').replace('\r', ' ');
    }

    public Path getArquivo() {
        return arquivo;
    }
}
