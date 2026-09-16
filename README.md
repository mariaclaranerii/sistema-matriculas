# Sistema de Matrículas

## Histórias de Usuário

### Gerar o currículo do semestre

> **Como** Secretaria, **quero** gerar o currículo de cada semestre, **para** organizar o respectivo período letivo.

Cada curso possui nome, número de créditos e é constituído por diversas disciplinas.

### Manter informações

> **Como** Secretaria, **quero** manter as informações sobre disciplinas, professores e alunos, **para** conservar esses dados no Sistema de Matrículas.

### Realizar matrícula

> **Como** Aluno, **quero** me matricular em disciplinas durante o período de matrículas, **para** registrar minha inscrição no semestre.

Os alunos podem se matricular em 4 disciplinas como primeira opção (obrigatórias) e em mais 2 alternativas (optativas). Cada disciplina admite no máximo 60 alunos; quando esse número é atingido, suas matrículas são encerradas.

Ao final do período de matrículas, a disciplina com pelo menos 3 alunos matriculados fica ativa para o semestre seguinte. Caso contrário, é cancelada.

### Cancelar matrícula

> **Como** Aluno, **quero** cancelar, durante o período de matrículas, uma matrícula feita anteriormente, **para** retirar uma inscrição que não desejo manter.

### Consultar alunos matriculados

> **Como** Professor, **quero** consultar os alunos matriculados em cada disciplina, **para** saber quem está inscrito nela.

### Receber notificação de matrícula

> **Como** Sistema de Cobranças, **quero** receber uma notificação após a inscrição de um aluno no semestre, **para** que ele possa ser cobrado pelas disciplinas daquele semestre.

### Validar o login

> **Como** Aluno, Professor ou Secretaria, **quero** utilizar minha senha para validar meu login, **para** ter meu acesso ao sistema validado.

## Diagrama de Casos de Uso

![Diagrama de Casos de Uso do Sistema de Matrículas](docs/diagrama-casos-de-uso.png)

[Arquivo-fonte em PlantUML](docs/diagrama-casos-de-uso.puml)
