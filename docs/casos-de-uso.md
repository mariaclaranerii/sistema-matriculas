# Casos de Uso - Sprint 1

## 1. Fronteira e atores

O sistema sob análise é o **Sistema de Matrículas**. O Sistema de Cobranças não
faz parte dessa fronteira: ele somente recebe a notificação prevista pelo
enunciado.

| Ator | Tipo | Responsabilidades no modelo |
|---|---|---|
| **Aluno** | Humano | Autenticar-se, realizar matrícula no semestre e cancelar matrículas anteriores durante o período permitido. |
| **Professor** | Humano | Autenticar-se e consultar os alunos matriculados por disciplina. |
| **Secretaria** | Humano (papel organizacional) | Autenticar-se, gerar o currículo semestral, manter informações de disciplinas, professores e alunos e, conforme **PM03**, solicitar o processamento de encerramento do período. |
| **Sistema de Cobranças** | Sistema externo | Receber a notificação de inscrição do Aluno no semestre para viabilizar a cobrança das disciplinas. |

> **Nota de modelagem:** UC01 é representado como caso de uso autônomo associado
> aos três atores humanos. Nos demais casos, a autenticação aparece como
> pré-condição, sem repetir relações `<<include>>` que tornariam o diagrama
> desnecessariamente carregado.

## 2. Resumo dos casos de uso

| ID | Caso de uso | Ator principal | Relação UML relevante |
|---|---|---|---|
| **UC01** | Autenticar usuário | Aluno, Professor ou Secretaria | - |
| **UC02** | Gerar currículo do semestre | Secretaria | - |
| **UC03** | Manter informações de disciplinas | Secretaria | - |
| **UC04** | Manter informações de professores | Secretaria | - |
| **UC05** | Manter informações de alunos | Secretaria | - |
| **UC06** | Realizar matrícula no semestre | Aluno | Inclui UC10 |
| **UC07** | Cancelar matrícula | Aluno | - |
| **UC08** | Processar encerramento do período de matrículas | Secretaria | - |
| **UC09** | Consultar alunos matriculados por disciplina | Professor | - |
| **UC10** | Notificar Sistema de Cobranças | Acionado por UC06; Sistema de Cobranças é receptor externo | Incluído por UC06 |

## 3. Especificações

### UC01 - Autenticar usuário

**Objetivo:** validar o login de um usuário humano por meio de sua senha antes
do acesso às funcionalidades do Sistema de Matrículas.

**Atores:** Aluno, Professor ou Secretaria.

**Pré-condições:**

- o usuário possui login e senha registrados.

**Fluxo principal:**

1. O usuário informa seu login e sua senha.
2. O sistema compara as informações fornecidas com as informações mantidas para o usuário.
3. O sistema valida o login.
4. O sistema libera o acesso às funcionalidades correspondentes ao ator autenticado.

**Fluxos alternativos/exceções:**

- **A1 - Login ou senha inválidos:** no passo 3, o sistema rejeita a autenticação e não libera o acesso.

**Pós-condições:**

- em caso de sucesso, o usuário está autenticado;
- em caso de falha, nenhuma funcionalidade restrita ao usuário é acessada.

**Relacionamentos:** RF01, RN07, US01.

### UC02 - Gerar currículo do semestre

**Objetivo:** permitir que a Secretaria gere o currículo referente a um
semestre.

**Atores:** Secretaria.

**Pré-condições:**

- a Secretaria está autenticada por UC01;
- as informações acadêmicas necessárias à composição estão disponíveis.

**Fluxo principal:**

1. A Secretaria identifica o semestre para o qual o currículo será gerado.
2. A Secretaria inicia a geração do currículo.
3. A Secretaria informa a composição acadêmica do semestre a partir dos cursos e das disciplinas.
4. O sistema registra a composição do currículo para o semestre informado, preservando a relação entre curso e disciplinas definida em RN01.
5. O sistema confirma a geração do currículo.

**Fluxos alternativos/exceções:**

- **A1 - Informações necessárias indisponíveis:** no passo 3, se não houver informações suficientes para compor o currículo, o sistema não conclui a geração.

**Pós-condições:**

- o currículo do semestre fica registrado no Sistema de Matrículas.

**Relacionamentos:** RF02, RN01, RN07, PM05, US02.

### UC03 - Manter informações de disciplinas

**Objetivo:** permitir que a Secretaria mantenha as informações das
disciplinas.

**Atores:** Secretaria.

**Pré-condições:**

- a Secretaria está autenticada por UC01.

**Fluxo principal:**

1. A Secretaria acessa a manutenção de disciplinas.
2. A Secretaria identifica a disciplina e as informações que precisam ser mantidas.
3. A Secretaria informa a manutenção desejada.
4. O sistema registra as informações fornecidas.
5. O sistema confirma a manutenção.

**Fluxos alternativos/exceções:**

- **A1 - Operação interrompida:** antes da confirmação, a Secretaria interrompe a operação e o sistema não registra mudanças.

**Pós-condições:**

- as informações da disciplina estão mantidas conforme a operação confirmada.

**Relacionamentos:** RF03, RN07, PM04, US03.

### UC04 - Manter informações de professores

**Objetivo:** permitir que a Secretaria mantenha as informações dos
professores.

**Atores:** Secretaria.

**Pré-condições:**

- a Secretaria está autenticada por UC01.

**Fluxo principal:**

1. A Secretaria acessa a manutenção de professores.
2. A Secretaria identifica o professor e as informações que precisam ser mantidas.
3. A Secretaria informa a manutenção desejada.
4. O sistema registra as informações fornecidas.
5. O sistema confirma a manutenção.

**Fluxos alternativos/exceções:**

- **A1 - Operação interrompida:** antes da confirmação, a Secretaria interrompe a operação e o sistema não registra mudanças.

**Pós-condições:**

- as informações do professor estão mantidas conforme a operação confirmada.

**Relacionamentos:** RF04, RN07, PM04, US04.

### UC05 - Manter informações de alunos

**Objetivo:** permitir que a Secretaria mantenha as informações dos alunos.

**Atores:** Secretaria.

**Pré-condições:**

- a Secretaria está autenticada por UC01.

**Fluxo principal:**

1. A Secretaria acessa a manutenção de alunos.
2. A Secretaria identifica o aluno e as informações que precisam ser mantidas.
3. A Secretaria informa a manutenção desejada.
4. O sistema registra as informações fornecidas.
5. O sistema confirma a manutenção.

**Fluxos alternativos/exceções:**

- **A1 - Operação interrompida:** antes da confirmação, a Secretaria interrompe a operação e o sistema não registra mudanças.

**Pós-condições:**

- as informações do aluno estão mantidas conforme a operação confirmada.

**Relacionamentos:** RF05, RN07, PM04, US05.

### UC06 - Realizar matrícula no semestre

**Objetivo:** permitir que o Aluno registre suas escolhas de disciplinas para um
semestre, respeitando o período, os limites de escolha e a lotação.

**Atores:** Aluno (principal) e Sistema de Cobranças (receptor externo por meio
de UC10).

**Pré-condições:**

- o Aluno está autenticado por UC01;
- o período de matrículas está em andamento;
- existe currículo gerado para o semestre.

**Fluxo principal:**

1. O Aluno acessa a matrícula do semestre.
2. O sistema apresenta as disciplinas do currículo daquele semestre disponíveis para seleção.
3. O Aluno seleciona até 4 disciplinas como primeira opção (obrigatórias) e até 2 disciplinas adicionais como alternativas (optativas).
4. O sistema verifica os limites de escolha e a lotação de cada disciplina selecionada.
5. O sistema registra as escolhas válidas e atualiza a quantidade de matriculados das respectivas disciplinas.
6. Se uma disciplina alcançar 60 matriculados, o sistema encerra novas matrículas para ela.
7. O sistema executa UC10 - Notificar Sistema de Cobranças.
8. O sistema confirma a matrícula no semestre.

**Fluxos alternativos/exceções:**

- **A1 - Fora do período:** antes do passo 2, se o período não estiver em andamento, o sistema não permite a matrícula.
- **A2 - Limite de escolhas excedido:** no passo 4, se houver mais de 4 primeiras opções ou mais de 2 alternativas, o sistema não confirma a matrícula enquanto a seleção não respeitar RN02.
- **A3 - Disciplina lotada:** no passo 4, se a disciplina já tiver 60 matriculados, o sistema rejeita a escolha dessa disciplina; não ocorre substituição automática por uma alternativa.
- **A4 - Falha na notificação:** no passo 7, segue-se UC10-E1. A política de retentativa ou de atomicidade entre matrícula e notificação não é definida pelo enunciado, conforme PM06.

**Pós-condições:**

- as escolhas válidas do Aluno estão registradas para o semestre;
- nenhuma disciplina ultrapassa 60 matriculados;
- após o fluxo principal completo, o Sistema de Cobranças foi notificado.

**Relacionamentos:** RF06, RF08, RF10, RN02, RN03, RN05, RN06, RN07, PM01,
PM02, PM06, PM10, US06.

### UC07 - Cancelar matrícula

**Objetivo:** permitir que o Aluno cancele uma matrícula realizada
anteriormente durante o período de matrículas.

**Atores:** Aluno.

**Pré-condições:**

- o Aluno está autenticado por UC01;
- o período de matrículas está em andamento;
- existe uma matrícula anterior a ser cancelada.

**Fluxo principal:**

1. O Aluno acessa suas matrículas anteriores do semestre.
2. O Aluno identifica a matrícula que deseja cancelar.
3. O Aluno confirma o cancelamento.
4. O sistema cancela a matrícula selecionada.
5. O sistema confirma o cancelamento ao Aluno.

**Fluxos alternativos/exceções:**

- **A1 - Fora do período:** antes do passo 2, se o período não estiver em andamento, o sistema não permite o cancelamento.
- **A2 - Matrícula inexistente:** no passo 4, se a matrícula indicada não existir ou já estiver cancelada, nenhuma alteração é realizada.

**Pós-condições:**

- a matrícula selecionada está cancelada;
- não é modelada notificação de cancelamento ao Sistema de Cobranças, conforme PM06.

**Relacionamentos:** RF07, RN03, RN07, PM06, PM10, US07.

### UC08 - Processar encerramento do período de matrículas

**Objetivo:** definir, ao final do período de matrículas, quais disciplinas
ficam ativas e quais são canceladas para o semestre seguinte.

**Atores:** Secretaria, conforme PM03.

**Pré-condições:**

- a Secretaria está autenticada por UC01;
- o período de matrículas terminou;
- as quantidades de matriculados por disciplina estão disponíveis.

**Fluxo principal:**

1. A Secretaria identifica o período encerrado e solicita seu processamento.
2. O sistema conta os alunos matriculados em cada disciplina.
3. Para cada disciplina com 3 ou mais matriculados, o sistema define o estado **ativa**.
4. Para cada disciplina com menos de 3 matriculados, o sistema define o estado **cancelada**.
5. O sistema registra o resultado do processamento.
6. O sistema apresenta o resultado à Secretaria.

**Fluxos alternativos/exceções:**

- **A1 - Período ainda em andamento:** no passo 1, se o período não terminou, o sistema não realiza a classificação final das disciplinas.

**Pós-condições:**

- cada disciplina processada está definida como ativa ou cancelada segundo RN04.

**Relacionamentos:** RF09, RN04, RN07, PM03, PM10, US08.

### UC09 - Consultar alunos matriculados por disciplina

**Objetivo:** permitir que o Professor saiba quais alunos estão matriculados em
uma disciplina.

**Atores:** Professor.

**Pré-condições:**

- o Professor está autenticado por UC01;
- a disciplina consultada está registrada no sistema.

**Fluxo principal:**

1. O Professor acessa a consulta de matriculados.
2. O Professor seleciona uma disciplina.
3. O sistema recupera os alunos matriculados na disciplina.
4. O sistema apresenta a relação de alunos ao Professor.

**Fluxos alternativos/exceções:**

- **A1 - Nenhum aluno matriculado:** no passo 3, se não houver matriculados, o sistema apresenta uma relação vazia.
- **A2 - Disciplina não encontrada:** no passo 3, se a disciplina não estiver registrada, o sistema não apresenta uma relação de matriculados.

**Pós-condições:**

- a relação de matriculados da disciplina foi apresentada, sem alterar matrículas.

**Relacionamentos:** RF11, RN07, PM07, US09.

### UC10 - Notificar Sistema de Cobranças

**Objetivo:** informar ao Sistema de Cobranças a inscrição do Aluno no semestre
e as disciplinas correspondentes, para viabilizar a cobrança.

**Atores:** Sistema de Cobranças (sistema externo receptor). O caso é iniciado
como inclusão obrigatória do fluxo bem-sucedido de UC06.

**Pré-condições:**

- a matrícula do Aluno no semestre foi registrada com sucesso em UC06;
- o sistema dispõe da identificação do Aluno, do semestre e das disciplinas registradas.

**Fluxo principal:**

1. O Sistema de Matrículas reúne a identificação do Aluno, do semestre e das disciplinas registradas.
2. O Sistema de Matrículas envia a notificação ao Sistema de Cobranças.
3. O Sistema de Cobranças recebe a notificação.
4. O Sistema de Matrículas conclui a notificação.

**Fluxos alternativos/exceções:**

- **E1 - Notificação não entregue:** no passo 3, se o Sistema de Cobranças não receber a notificação, o caso termina sem confirmação de entrega. O enunciado não define retentativa ou compensação.

**Pós-condições:**

- no fluxo principal, o Sistema de Cobranças está notificado sobre a inscrição do Aluno no semestre;
- em E1, a recuperação permanece não especificada, conforme PM06.

**Relacionamentos:** RF10, RN06, PM06, US06, US10.

## 4. Relações UML adotadas

- **UC06 `<<include>>` UC10:** a notificação é parte obrigatória do fluxo de matrícula bem-sucedido descrito pelo enunciado.
- **Nenhum `<<extend>>`:** os fluxos opcionais ou de erro não constituem novos objetivos independentes de ator.
- **Nenhuma generalização:** os atores humanos têm objetivos próprios; criar um ator abstrato “Usuário” não acrescentaria informação essencial a este diagrama.
