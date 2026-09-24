# Premissas de implementação

As decisões abaixo viabilizam um protótipo utilizável sem atribuir ao Product
Owner requisitos que não constam no enunciado.

| ID | Premissa | Justificativa |
|---|---|---|
| **PI01** | O login identifica cada usuário de maneira única. | O enunciado exige login e senha, mas não define matrícula, código funcional ou outro identificador. |
| **PI02** | A disciplina recebe apenas um nome para identificação na interface e na persistência. | É necessário distinguir as disciplinas durante cadastro, matrícula e consulta, sem acrescentar uma ficha acadêmica não especificada. |
| **PI03** | Uma matrícula semestral agrupa todas as escolhas do Aluno e cada escolha pode ser cancelada individualmente. | O enunciado permite cancelar matrículas anteriores e o modelo associa cada escolha a uma oferta. |
| **PI04** | A ação de processamento solicitada pela Secretaria encerra o período e classifica as ofertas na mesma operação. | O enunciado define o resultado ao final do período, mas não atribui a nenhum ator uma operação separada para alterar seu estado. |
| **PI05** | Cada Aluno possui no máximo uma matrícula semestral por currículo. | As escolhas de até 4+2 disciplinas são tratadas como uma única inscrição do Aluno naquele semestre. |
| **PI06** | Os objetos do sistema são serializados em um arquivo local. | O enunciado permite persistência em arquivos e não exige banco de dados ou formato específico. |
| **PI07** | A notificação ao Sistema de Cobranças é simulada por uma linha em arquivo de texto. | O sistema externo e seu protocolo não foram fornecidos; o arquivo torna a integração demonstrável. |
| **PI08** | Na ausência de arquivo persistido, são criados usuários e dados acadêmicos mínimos de demonstração. | Isso permite avaliar imediatamente os fluxos sem depender de preparação manual anterior. |
| **PI09** | As alternativas são registradas como escolhas, sem substituição automática. | Não foram definidos prioridade, momento nem algoritmo de substituição. |

As credenciais iniciais são destinadas exclusivamente à demonstração acadêmica;
o protótipo não representa um mecanismo de segurança para ambiente de produção.
