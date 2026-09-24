package br.pucminas.sistemamatriculas.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Persiste o estado completo do protótipo em um arquivo local.
 */
public class PersistenciaArquivo {
    private final Path arquivo;

    public PersistenciaArquivo(Path arquivo) {
        this.arquivo = arquivo.toAbsolutePath().normalize();
    }

    public EstadoSistema carregar() throws IOException {
        if (Files.notExists(arquivo)) {
            return new EstadoSistema();
        }
        try (InputStream entrada = Files.newInputStream(arquivo);
                ObjectInputStream objetos = new ObjectInputStream(entrada)) {
            Object objeto = objetos.readObject();
            if (!(objeto instanceof EstadoSistema estado)) {
                throw new IOException("O arquivo de dados não contém um estado válido.");
            }
            return estado;
        } catch (ClassNotFoundException | ClassCastException excecao) {
            throw new IOException("Não foi possível interpretar o arquivo de dados.", excecao);
        }
    }

    public void salvar(EstadoSistema estado) throws IOException {
        Path diretorio = arquivo.getParent();
        if (diretorio != null) {
            Files.createDirectories(diretorio);
        }
        Path temporario = arquivo.resolveSibling(arquivo.getFileName() + ".tmp");
        try (OutputStream saida = Files.newOutputStream(temporario);
                ObjectOutputStream objetos = new ObjectOutputStream(saida)) {
            objetos.writeObject(estado);
        }
        try {
            Files.move(temporario, arquivo,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException excecao) {
            Files.move(temporario, arquivo, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public Path getArquivo() {
        return arquivo;
    }
}
