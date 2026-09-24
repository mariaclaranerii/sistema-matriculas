# Requisitos

## 1. Fonte e escopo

Este documento consolida o Modelo de Análise do Sistema de Matrículas. A única
fonte de requisitos é o enunciado `LABORATORIO_2_LAB_DESENVOLVIMENTO_DE_SOFTWARE.pdf`.

Os termos **deve** e **devem** indicam comportamento obrigatório. Informações
necessárias à modelagem, mas não definidas pelo enunciado, estão explicitadas na
seção [Premissas de modelagem](#5-premissas-de-modelagem).

## 2. Requisitos funcionais

| ID | Requisito funcional | Evidência no enunciado |
|---|---|---|
| **RF01** | O sistema deve autenticar Aluno, Professor e Secretaria, validando o login por meio da senha do respectivo usuário. | Todos os usuários possuem senhas usadas na validação do login. |
| **RF02** | O sistema deve permitir que a Secretaria gere o currículo de cada semestre. | A Secretaria gera o currículo para cada semestre. |
| **RF03** | O sistema deve permitir que a Secretaria mantenha as informações das disciplinas. | A Secretaria mantém informações sobre disciplinas. |
| **RF04** | O sistema deve permitir que a Secretaria mantenha as informações dos professores. | A Secretaria mantém informações sobre professores. |
| **RF05** | O sistema deve permitir que a Secretaria mantenha as informações dos alunos. | A Secretaria mantém informações sobre alunos. |
| **RF06** | O sistema deve permitir que o Aluno realize sua matrícula em disciplinas durante o período de matrículas. | Durante esse período, o aluno pode acessar o sistema para se matricular em disciplinas. |
| **RF07** | O sistema deve permitir que o Aluno cancele matrículas realizadas anteriormente, desde que esteja no período de matrículas. | O aluno pode cancelar matrículas anteriormente realizadas durante o período. |
| **RF08** | O sistema deve controlar a lotação de cada disciplina e encerrar novas matrículas nela quando alcançar 60 alunos matriculados. | A disciplina comporta no máximo 60 inscritos; ao atingir o limite, suas matrículas são encerradas. |
| **RF09** | Ao final do período de matrículas, o sistema deve definir como ativa a disciplina com pelo menos 3 alunos matriculados e cancelar a disciplina com menos de 3. | A ocorrência da disciplina no semestre seguinte depende do mínimo de 3 matriculados. |
| **RF10** | Após a inscrição de um Aluno em um semestre, o Sistema de Matrículas deve notificar o Sistema de Cobranças para viabilizar a cobrança das disciplinas daquele semestre. | O Sistema de Cobranças é notificado após a inscrição no semestre. |
| **RF11** | O sistema deve permitir que o Professor consulte quais alunos estão matriculados em cada disciplina. | Professores podem acessar o sistema para saber os alunos matriculados em cada disciplina. |

## 3. Regras de negócio

| ID | Regra de negócio |
|---|---|
| **RN01** | Cada curso possui um nome, um determinado número de créditos e é constituído por diversas disciplinas. |
| **RN02** | Em cada semestre, o Aluno pode selecionar até 4 disciplinas como primeira opção (obrigatórias) e até 2 disciplinas adicionais como alternativas (optativas), conforme a interpretação registrada em **PM01**. |
| **RN03** | A realização e o cancelamento de matrículas somente são permitidos durante o período de matrículas. |
| **RN04** | No encerramento do período de matrículas, uma disciplina com 3 ou mais matriculados fica ativa para o semestre seguinte; com menos de 3, é cancelada. |
| **RN05** | Uma disciplina admite no máximo 60 alunos matriculados. A 60ª matrícula válida completa a lotação; depois disso, nenhuma nova matrícula pode ser registrada nessa disciplina. |
| **RN06** | Uma inscrição bem-sucedida do Aluno no semestre deve resultar em uma notificação ao Sistema de Cobranças, identificando o aluno e as disciplinas daquele semestre necessárias à cobrança. |
| **RN07** | Aluno, Professor e Secretaria possuem senha, utilizada para validar o respectivo login antes do acesso às suas funcionalidades. |

## 4. Restrições relevantes

| ID | Restrição |
|---|---|
| **REST01** | A Sprint 1 é documental e deve conter o Diagrama de Casos de Uso e as Histórias de Usuário em Markdown no README do repositório. |
| **REST02** | Esta entrega não contém Java, diagrama de classes, classes, atributos, métodos, stubs, banco de dados, interface, persistência nem protótipo funcional; esses elementos pertencem às sprints posteriores. |
| **REST03** | O enunciado determina Java para o sistema a ser desenvolvido nas sprints posteriores; essa tecnologia não é aplicada na Sprint 1. |
| **REST04** | O repositório GitHub deve permanecer atualizado com as versões produzidas dos modelos UML e, futuramente, do código. |
| **REST05** | O enunciado não define atributos de Aluno, Professor ou Disciplina, calendário/formato do período, protocolo da integração, política de repetição de notificações, interface ou mecanismo de persistência; tais decisões não fazem parte deste modelo. |

## 5. Premissas de modelagem

| ID | Premissa de modelagem | Justificativa |
|---|---|---|
| **PM01** | As quantidades de 4 disciplinas de primeira opção e 2 alternativas são tratadas como limites máximos, e não como obrigação de preencher exatamente seis escolhas. | O enunciado usa “podem se matricular” e não informa uma carga mínima. |
| **PM02** | As disciplinas alternativas são apenas registradas como escolhas optativas; não foi modelada substituição automática de uma primeira opção por uma alternativa. | O enunciado não define prioridade, momento ou algoritmo de substituição. |
| **PM03** | Para dar um iniciador humano ao caso de uso de encerramento, considera-se que a Secretaria solicita o processamento ao fim do período. | O enunciado define o resultado do encerramento, mas não informa se ele é agendado nem quem o dispara. |
| **PM04** | “Manter informações” permanece uma capacidade genérica de manutenção dos dados indicados, sem decomposição em operações ou atributos não citados. | Detalhes de cadastro e campos não são fornecidos. |
| **PM05** | O currículo do semestre é tratado como o registro da composição acadêmica daquele semestre a partir de cursos e disciplinas; sua estrutura interna não é detalhada. | O enunciado exige a geração do currículo, mas não define seus campos ou processo de montagem. |
| **PM06** | A notificação ao Sistema de Cobranças é uma interação abstrata e posterior à matrícula bem-sucedida. Não são definidos transporte, sincronismo, retentativa nem aviso de cancelamento. | O enunciado informa apenas a finalidade e o momento da notificação de inscrição. |
| **PM07** | A consulta do Professor recebe uma disciplina selecionada e retorna seus matriculados, sem restringir a consulta apenas a disciplinas atribuídas ao professor. | O enunciado não define vínculo entre professor e oferta nem regra de autorização por disciplina. |
| **PM08** | Aluno, Professor e Secretaria são atores humanos; o Sistema de Cobranças é um sistema externo ao Sistema de Matrículas. | A separação preserva a fronteira do sistema e a natureza dos participantes citados. |
| **PM09** | Não há caso de uso autônomo para manter cursos. As características de Curso são tratadas como regra do domínio e como contexto da geração curricular. | O enunciado atribui expressamente à Secretaria a manutenção de disciplinas, professores e alunos, mas não a manutenção de cursos. |
| **PM10** | O período de matrículas é tratado como uma condição temporal já existente. Não são modeladas funcionalidades para criar, abrir, alterar ou agendar períodos. | O enunciado informa que há períodos para matrícula, mas não atribui a nenhum ator a gestão desses períodos. |

## 6. Matriz de rastreabilidade

### 6.1 Requisito funcional -> Caso de Uso -> História de Usuário

| Requisito | Caso(s) de uso | História(s) de usuário |
|---|---|---|
| **RF01** | [UC01 - Autenticar usuário](casos-de-uso.md#uc01---autenticar-usuário) | **US01** |
| **RF02** | [UC02 - Gerar currículo do semestre](casos-de-uso.md#uc02---gerar-currículo-do-semestre) | **US02** |
| **RF03** | [UC03 - Manter informações de disciplinas](casos-de-uso.md#uc03---manter-informações-de-disciplinas) | **US03** |
| **RF04** | [UC04 - Manter informações de professores](casos-de-uso.md#uc04---manter-informações-de-professores) | **US04** |
| **RF05** | [UC05 - Manter informações de alunos](casos-de-uso.md#uc05---manter-informações-de-alunos) | **US05** |
| **RF06** | [UC06 - Realizar matrícula no semestre](casos-de-uso.md#uc06---realizar-matrícula-no-semestre) | **US06** |
| **RF07** | [UC07 - Cancelar matrícula](casos-de-uso.md#uc07---cancelar-matrícula) | **US07** |
| **RF08** | [UC06 - Realizar matrícula no semestre](casos-de-uso.md#uc06---realizar-matrícula-no-semestre) | **US06** |
| **RF09** | [UC08 - Processar encerramento do período](casos-de-uso.md#uc08---processar-encerramento-do-período-de-matrículas) | **US08** |
| **RF10** | [UC10 - Notificar Sistema de Cobranças](casos-de-uso.md#uc10---notificar-sistema-de-cobranças), incluído por **UC06** | **US06**, **US10** |
| **RF11** | [UC09 - Consultar alunos matriculados por disciplina](casos-de-uso.md#uc09---consultar-alunos-matriculados-por-disciplina) | **US09** |

### 6.2 Regra de negócio -> Caso de Uso -> História de Usuário

| Regra | Caso(s) de uso | História(s) de usuário |
|---|---|---|
| **RN01** | **UC02** | **US02** |
| **RN02** | **UC06** | **US06** |
| **RN03** | **UC06**, **UC07** | **US06**, **US07** |
| **RN04** | **UC08** | **US08** |
| **RN05** | **UC06** | **US06** |
| **RN06** | **UC06**, **UC10** | **US06**, **US10** |
| **RN07** | **UC01** e pré-condição de **UC02** a **UC09** | **US01** a **US09** |

## 7. Cobertura do enunciado

| Elemento citado no enunciado | Cobertura no modelo |
|---|---|
| Secretaria gera currículo por semestre | RF02, UC02, US02 |
| Secretaria mantém disciplinas, professores e alunos | RF03-RF05, UC03-UC05, US03-US05 |
| Curso possui nome, créditos e disciplinas | RN01, UC02, US02 |
| 4 primeiras opções e 2 alternativas | RN02, UC06, US06, PM01-PM02 |
| Período para matrícula e cancelamento | RF06-RF07, RN03, UC06-UC07, US06-US07 |
| Mínimo de 3 alunos para ativação | RF09, RN04, UC08, US08 |
| Máximo de 60 alunos e encerramento de inscrições | RF08, RN05, UC06, US06 |
| Notificação ao Sistema de Cobranças | RF10, RN06, UC10, US10 |
| Consulta de matriculados pelo Professor | RF11, UC09, US09 |
| Senha para validação de login | RF01, RN07, UC01, US01 |
