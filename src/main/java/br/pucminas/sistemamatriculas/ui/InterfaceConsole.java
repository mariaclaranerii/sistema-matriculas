package br.pucminas.sistemamatriculas.ui;

import br.pucminas.sistemamatriculas.acesso.Aluno;
import br.pucminas.sistemamatriculas.acesso.Professor;
import br.pucminas.sistemamatriculas.acesso.Secretaria;
import br.pucminas.sistemamatriculas.acesso.Usuario;
import br.pucminas.sistemamatriculas.aplicacao.SistemaMatriculas;
import br.pucminas.sistemamatriculas.dominio.RegraNegocioException;
import br.pucminas.sistemamatriculas.dominio.academico.CurriculoSemestral;
import br.pucminas.sistemamatriculas.dominio.academico.Curso;
import br.pucminas.sistemamatriculas.dominio.academico.Disciplina;
import br.pucminas.sistemamatriculas.dominio.academico.OfertaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaDisciplina;
import br.pucminas.sistemamatriculas.dominio.matricula.MatriculaSemestral;
import br.pucminas.sistemamatriculas.persistencia.EstadoSistema;
import br.pucminas.sistemamatriculas.persistencia.PersistenciaArquivo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

/**
 * Interface em linha de comando para os três papéis humanos do sistema.
 */
public class InterfaceConsole {
    private final SistemaMatriculas sistema;
    private final EstadoSistema estado;
    private final PersistenciaArquivo persistencia;
    private final Scanner entrada;

    public InterfaceConsole(
            SistemaMatriculas sistema,
            PersistenciaArquivo persistencia,
            Scanner entrada) {
        this.sistema = sistema;
        this.estado = sistema.getEstado();
        this.persistencia = persistencia;
        this.entrada = entrada;
    }

    public void executar() {
        exibirCabecalho();
        boolean executando = true;
        while (executando) {
            System.out.println("\n1 - Entrar");
            System.out.println("0 - Sair");
            int opcao = lerInteiro("Escolha: ", 0, 1);
            if (opcao == 0) {
                executando = false;
            } else {
                autenticarEExibirMenu();
            }
        }
        System.out.println("Dados salvos. Até logo!");
    }

    private void autenticarEExibirMenu() {
        String login = lerTexto("Login: ");
        String senha = lerTexto("Senha: ");
        try {
            Usuario usuario = sistema.autenticar(login, senha);
            System.out.println("\nBem-vindo(a), " + usuario.getLogin() + ".");
            if (usuario instanceof Secretaria secretaria) {
                menuSecretaria(secretaria);
            } else if (usuario instanceof Aluno aluno) {
                menuAluno(aluno);
            } else if (usuario instanceof Professor professor) {
                menuProfessor(professor);
            }
        } catch (RegraNegocioException excecao) {
            exibirErro(excecao);
        }
    }

    private void menuSecretaria(Secretaria secretaria) {
        int opcao;
        do {
            System.out.println("\n=== Secretaria ===");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Cadastrar professor");
            System.out.println("3 - Cadastrar disciplina");
            System.out.println("4 - Gerar currículo semestral");
            System.out.println("5 - Encerrar período e processar ofertas");
            System.out.println("6 - Consultar resumo acadêmico");
            System.out.println("0 - Encerrar sessão");
            opcao = lerInteiro("Escolha: ", 0, 6);
            try {
                switch (opcao) {
                    case 1 -> cadastrarAluno();
                    case 2 -> cadastrarProfessor();
                    case 3 -> cadastrarDisciplina();
                    case 4 -> gerarCurriculo();
                    case 5 -> processarEncerramento();
                    case 6 -> exibirResumo();
                    default -> {
                    }
                }
            } catch (RegraNegocioException | IllegalArgumentException excecao) {
                exibirErro(excecao);
            }
        } while (opcao != 0);
    }

    private void menuAluno(Aluno aluno) {
        int opcao;
        do {
            System.out.println("\n=== Aluno ===");
            System.out.println("1 - Consultar ofertas");
            System.out.println("2 - Realizar matrícula");
            System.out.println("3 - Cancelar matrícula em disciplina");
            System.out.println("4 - Consultar minhas matrículas");
            System.out.println("0 - Encerrar sessão");
            opcao = lerInteiro("Escolha: ", 0, 4);
            try {
                switch (opcao) {
                    case 1 -> consultarOfertas();
                    case 2 -> realizarMatricula(aluno);
                    case 3 -> cancelarMatricula(aluno);
                    case 4 -> exibirMatriculas(aluno);
                    default -> {
                    }
                }
            } catch (RegraNegocioException | IllegalArgumentException excecao) {
                exibirErro(excecao);
            }
        } while (opcao != 0);
    }

    private void menuProfessor(Professor professor) {
        int opcao;
        do {
            System.out.println("\n=== Professor ===");
            System.out.println("1 - Consultar alunos por disciplina");
            System.out.println("0 - Encerrar sessão");
            opcao = lerInteiro("Escolha: ", 0, 1);
            if (opcao == 1) {
                consultarAlunosPorDisciplina();
            }
        } while (opcao != 0);
    }

    private void cadastrarAluno() {
        sistema.manterAluno(new Aluno(
                lerTexto("Novo login do aluno: "),
                lerTexto("Senha: ")));
        salvar();
        System.out.println("Aluno cadastrado.");
    }

    private void cadastrarProfessor() {
        sistema.manterProfessor(new Professor(
                lerTexto("Novo login do professor: "),
                lerTexto("Senha: ")));
        salvar();
        System.out.println("Professor cadastrado.");
    }

    private void cadastrarDisciplina() {
        sistema.manterDisciplina(new Disciplina(lerTexto("Nome da disciplina: ")));
        salvar();
        System.out.println("Disciplina cadastrada.");
    }

    private void gerarCurriculo() {
        if (estado.getCursos().isEmpty()) {
            throw new RegraNegocioException("Cadastre ao menos um curso primeiro.");
        }
        String identificador = lerTexto("Identificador do semestre (ex.: 2026/2): ");
        List<Curso> cursos = selecionarVarios(
                estado.getCursos(), Curso::toString,
                "Cursos do currículo (números separados por vírgula): ", false);
        CurriculoSemestral curriculo = sistema.gerarCurriculo(identificador, cursos);
        salvar();
        System.out.println("Currículo gerado com "
                + curriculo.listarOfertas().size() + " ofertas.");
    }

    private void processarEncerramento() {
        List<CurriculoSemestral> abertos = estado.getCurriculos().stream()
                .filter(curriculo -> curriculo.getPeriodoMatricula().estaEmAndamento())
                .toList();
        CurriculoSemestral curriculo = selecionarUm(
                abertos, CurriculoSemestral::toString, "Currículo a encerrar: ");
        if (curriculo == null) {
            return;
        }
        String confirmacao = lerTexto(
                "Confirma o encerramento definitivo de "
                        + curriculo.getIdentificadorSemestre() + "? (s/n): ");
        if (!confirmacao.equalsIgnoreCase("s")) {
            System.out.println("Operação cancelada.");
            return;
        }
        sistema.processarEncerramento(curriculo);
        salvar();
        System.out.println("Período encerrado e ofertas processadas:");
        curriculo.listarOfertas().forEach(oferta -> System.out.println("- " + oferta));
    }

    private void exibirResumo() {
        System.out.println("\nAlunos: " + estado.getAlunos().size());
        System.out.println("Professores: " + estado.getProfessores().size());
        System.out.println("Disciplinas: " + estado.getDisciplinas().size());
        System.out.println("Cursos: " + estado.getCursos().size());
        System.out.println("Currículos: " + estado.getCurriculos().size());
        System.out.println("Matrículas semestrais: " + estado.getMatriculas().size());
        estado.getCurriculos().forEach(curriculo -> {
            System.out.println("\n" + curriculo);
            curriculo.listarOfertas().forEach(oferta -> System.out.println("  - " + oferta));
        });
    }

    private void consultarOfertas() {
        CurriculoSemestral curriculo = selecionarUm(
                estado.getCurriculos(), CurriculoSemestral::toString,
                "Currículo: ");
        if (curriculo == null) {
            return;
        }
        System.out.println("\nOfertas de " + curriculo.getIdentificadorSemestre() + ":");
        curriculo.listarOfertas().forEach(oferta -> System.out.println("- " + oferta));
    }

    private void realizarMatricula(Aluno aluno) {
        List<CurriculoSemestral> disponiveis = estado.getCurriculos().stream()
                .filter(curriculo -> curriculo.getPeriodoMatricula().estaEmAndamento())
                .filter(curriculo -> estado.localizarMatricula(aluno, curriculo).isEmpty())
                .toList();
        CurriculoSemestral curriculo = selecionarUm(
                disponiveis, CurriculoSemestral::toString,
                "Currículo para matrícula: ");
        if (curriculo == null) {
            return;
        }
        List<OfertaDisciplina> ofertas = curriculo.listarOfertas().stream()
                .filter(OfertaDisciplina::podeReceberMatricula)
                .toList();
        List<OfertaDisciplina> primeiras = selecionarVarios(
                ofertas, OfertaDisciplina::toString,
                "Primeiras opções, de 1 a 4 (números separados por vírgula): ", false);
        if (primeiras.size() > 4) {
            throw new RegraNegocioException("Escolha no máximo 4 primeiras opções.");
        }
        List<OfertaDisciplina> restantes = ofertas.stream()
                .filter(oferta -> !primeiras.contains(oferta))
                .toList();
        List<OfertaDisciplina> alternativas = selecionarVarios(
                restantes, OfertaDisciplina::toString,
                "Alternativas, até 2 (Enter para nenhuma): ", true);
        if (alternativas.size() > 2) {
            throw new RegraNegocioException("Escolha no máximo 2 alternativas.");
        }
        MatriculaSemestral matricula = sistema.realizarMatricula(
                aluno, curriculo, primeiras, alternativas);
        salvar();
        System.out.println("Matrícula confirmada com "
                + matricula.getMatriculas().size() + " disciplinas.");
    }

    private void cancelarMatricula(Aluno aluno) {
        List<MatriculaDisciplina> cancelaveis = sistema.consultarMatriculas(aluno).stream()
                .filter(matricula -> matricula.getCurriculo()
                        .getPeriodoMatricula().estaEmAndamento())
                .flatMap(matricula -> matricula.getMatriculasAtivas().stream())
                .toList();
        MatriculaDisciplina matricula = selecionarUm(
                cancelaveis, MatriculaDisciplina::toString,
                "Matrícula em disciplina a cancelar: ");
        if (matricula == null) {
            return;
        }
        sistema.cancelarMatricula(matricula);
        salvar();
        System.out.println("Matrícula em disciplina cancelada.");
    }

    private void exibirMatriculas(Aluno aluno) {
        List<MatriculaSemestral> matriculas = sistema.consultarMatriculas(aluno);
        if (matriculas.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada.");
            return;
        }
        matriculas.forEach(matricula -> {
            System.out.println("\n" + matricula.getCurriculo());
            matricula.getMatriculas().forEach(item -> System.out.println("- " + item));
        });
    }

    private void consultarAlunosPorDisciplina() {
        CurriculoSemestral curriculo = selecionarUm(
                estado.getCurriculos(), CurriculoSemestral::toString,
                "Currículo: ");
        if (curriculo == null) {
            return;
        }
        OfertaDisciplina oferta = selecionarUm(
                curriculo.listarOfertas(), OfertaDisciplina::toString,
                "Disciplina: ");
        if (oferta == null) {
            return;
        }
        List<Aluno> alunos = sistema.consultarAlunos(oferta);
        System.out.println("\nMatriculados em " + oferta.getDisciplina().getNome() + ":");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno matriculado.");
        } else {
            alunos.forEach(aluno -> System.out.println("- " + aluno.getLogin()));
        }
    }

    private <T> T selecionarUm(
            List<T> itens,
            Function<T, String> descricao,
            String mensagem) {
        if (itens.isEmpty()) {
            System.out.println("Não há opções disponíveis.");
            return null;
        }
        for (int indice = 0; indice < itens.size(); indice++) {
            System.out.println((indice + 1) + " - " + descricao.apply(itens.get(indice)));
        }
        int escolha = lerInteiro(mensagem + "(0 para cancelar): ", 0, itens.size());
        return escolha == 0 ? null : itens.get(escolha - 1);
    }

    private <T> List<T> selecionarVarios(
            List<T> itens,
            Function<T, String> descricao,
            String mensagem,
            boolean permiteVazio) {
        if (itens.isEmpty()) {
            if (permiteVazio) {
                return List.of();
            }
            throw new RegraNegocioException("Não há opções disponíveis.");
        }
        for (int indice = 0; indice < itens.size(); indice++) {
            System.out.println((indice + 1) + " - " + descricao.apply(itens.get(indice)));
        }
        while (true) {
            String resposta = lerTextoPermitindoVazio(mensagem);
            if (resposta.isBlank() && permiteVazio) {
                return List.of();
            }
            try {
                Set<Integer> indices = new LinkedHashSet<>();
                for (String parte : resposta.split("[,;\\s]+")) {
                    int indice = Integer.parseInt(parte);
                    if (indice < 1 || indice > itens.size()) {
                        throw new NumberFormatException();
                    }
                    indices.add(indice - 1);
                }
                if (indices.isEmpty() && !permiteVazio) {
                    throw new NumberFormatException();
                }
                List<T> selecionados = new ArrayList<>();
                indices.forEach(indice -> selecionados.add(itens.get(indice)));
                return selecionados;
            } catch (NumberFormatException excecao) {
                System.out.println("Informe somente os números exibidos, separados por vírgula.");
            }
        }
    }

    private String lerTexto(String mensagem) {
        while (true) {
            String valor = lerTextoPermitindoVazio(mensagem);
            if (!valor.isBlank()) {
                return valor;
            }
            System.out.println("O valor não pode ficar vazio.");
        }
    }

    private String lerTextoPermitindoVazio(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            String valor = entrada.nextLine().trim();
            try {
                int numero = Integer.parseInt(valor);
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
            } catch (NumberFormatException excecao) {
                // A mensagem comum abaixo também cobre entradas não numéricas.
            }
            System.out.println("Informe um número entre " + minimo + " e " + maximo + ".");
        }
    }

    private void salvar() {
        try {
            persistencia.salvar(estado);
        } catch (IOException excecao) {
            throw new RegraNegocioException(
                    "A operação foi realizada, mas não foi possível salvar os dados.", excecao);
        }
    }

    private void exibirCabecalho() {
        System.out.println("====================================");
        System.out.println("       SISTEMA DE MATRÍCULAS");
        System.out.println("====================================");
    }

    private void exibirErro(RuntimeException excecao) {
        System.out.println("Não foi possível concluir: " + excecao.getMessage());
    }
}
