# Guia de execução

O Sistema de Matrículas possui uma interface em linha de comando e grava os
dados localmente em arquivos. É necessário utilizar o JDK 17.

## Executar pelo VS Code

1. Abra a pasta raiz do repositório, aquela que contém o arquivo `pom.xml`.
2. Aguarde a indicação `Java: Ready` na barra inferior.
3. Abra `src/main/java/br/pucminas/sistemamatriculas/Main.java`.
4. Clique em **Run** acima do método `main` ou pressione `F5`.
5. Utilize o terminal integrado para interagir com os menus.

Não é necessário instalar o Maven para executar dessa forma.

## Acessos iniciais

Na primeira execução, o sistema cria um conjunto pequeno de dados para permitir
a demonstração imediata das funcionalidades.

| Papel | Login | Senha |
|---|---|---|
| Secretaria | `secretaria` | `123` |
| Professor | `professor` | `123` |
| Aluno | `aluno` | `123` |

A Secretaria pode cadastrar outros alunos e professores pelos menus.

## Funcionalidades disponíveis

- autenticação de Aluno, Professor e Secretaria;
- cadastro de alunos, professores e disciplinas;
- geração do currículo semestral;
- matrícula em até 4 primeiras opções e até 2 alternativas;
- cancelamento durante o período de matrículas;
- bloqueio de novas matrículas quando uma oferta alcança 60 alunos;
- encerramento do período, ativando ofertas com pelo menos 3 matriculados e
  cancelando as demais;
- consulta de matriculados por disciplina pelo Professor;
- notificação da inscrição ao Sistema de Cobranças;
- persistência automática dos dados.

## Arquivos gerados

Os arquivos abaixo são criados na pasta `dados` e não são enviados ao GitHub:

- `sistema-matriculas.dat`: estado persistido do sistema;
- `notificacoes-cobranca.txt`: registro legível das notificações enviadas ao
  Sistema de Cobranças.

Para iniciar novamente com os dados originais, feche o programa e remova a
pasta `dados` do computador.

## Execução com Maven

Esta alternativa é opcional e requer Maven instalado:

```bash
mvn clean package
java -jar target/sistema-matriculas-1.0-SNAPSHOT.jar
```

Também é possível informar outra pasta para os arquivos de dados:

```bash
java -jar target/sistema-matriculas-1.0-SNAPSHOT.jar meus-dados
```

## Documentação técnica

- [Diagrama de Classes](diagrama-classes.png) e
  [fonte PlantUML](diagrama-classes.puml);
- [Diagrama de Arquitetura](diagrama-arquitetura.png) e
  [fonte PlantUML](diagrama-arquitetura.puml);
- [Premissas de implementação](premissas-implementacao.md).
