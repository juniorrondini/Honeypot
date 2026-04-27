# AgendaPro Beauty - Analise de Produto e Melhorias

## O Que E O Aplicativo

AgendaPro Beauty e um aplicativo Android nativo para gestao operacional de pequenos negocios de beleza.

Publico principal:

- barbearias;
- saloes pequenos;
- profissionais autonomos;
- manicures;
- cabeleireiros;
- lash designers;
- designers de sobrancelha.

Proposta central:

> Ajudar o dono ou profissional a controlar equipe, clientes, servicos, agenda e dinheiro recebido.

## Estado Atual

O projeto tem uma base tecnica funcional:

- Kotlin;
- Android nativo;
- Jetpack Compose;
- Material 3;
- MVVM;
- Clean Architecture simples;
- Room;
- DataStore;
- Coroutines e Flow;
- Navigation Compose.

O app ja possui:

- onboarding;
- dashboard;
- equipe;
- clientes;
- servicos;
- agenda diaria;
- criacao de agendamento;
- cancelamento e conclusao;
- financeiro simples;
- configuracoes;
- tela premium inicial.

## Problema Principal

O app ainda nao esta pronto como produto SaaS profissional.

Os principais problemas sao:

1. Nao existe separacao real de ambientes.
2. O visual ainda parece prototipo.
3. O fluxo de agendamento ainda exige muita digitacao manual.
4. Cliente e servico cadastrados ainda nao sao aproveitados corretamente no agendamento.
5. Nao existe controle de permissoes.
6. Nao existe conceito claro de usuario logado.
7. Nao existe painel administrativo completo.
8. Nao existe ambiente do profissional separado.
9. Nao existe ambiente do cliente final.
10. A tela premium ainda nao demonstra valor suficiente para assinatura.

## Ambientes Necessarios

### 1. Ambiente Administrador

Usuario: dono do salao, gerente ou profissional autonomo dono da conta.

Permissoes:

- editar dados da empresa;
- cadastrar profissionais;
- ativar/desativar profissionais;
- cadastrar servicos;
- cadastrar clientes;
- ver agenda de todos;
- criar agendamentos para qualquer profissional;
- cancelar/concluir atendimentos;
- ver financeiro completo;
- ver relatorios;
- acessar configuracoes;
- gerenciar plano premium.

Telas principais:

- Dashboard da empresa;
- Agenda geral;
- Equipe;
- Clientes;
- Servicos;
- Financeiro;
- Relatorios;
- Premium;
- Configuracoes.

### 2. Ambiente Profissional

Usuario: barbeiro, manicure, cabeleireiro ou prestador dentro da empresa.

Permissoes:

- ver propria agenda;
- ver horarios livres;
- marcar atendimento como concluido;
- marcar falta;
- ver dados basicos do cliente;
- adicionar observacao de atendimento;
- opcionalmente criar agendamento proprio.

Restricoes:

- nao deve ver financeiro completo da empresa;
- nao deve alterar plano;
- nao deve excluir outros profissionais;
- nao deve ver relatorios administrativos sensiveis.

Telas principais:

- Minha agenda;
- Meus horarios;
- Meus clientes;
- Historico de atendimentos;
- Perfil.

### 3. Ambiente Cliente Final

Usuario: pessoa que agenda um corte, unha, sobrancelha etc.

Esse ambiente nao deve entrar no MVP Android local-first agora. Ele deve ser fase futura, provavelmente por link web ou mini portal.

Funcionalidades futuras:

- escolher profissional;
- ver horarios disponiveis;
- escolher servico;
- solicitar agendamento;
- receber confirmacao;
- receber lembrete.

## Veredito Sobre O Estado Atual

O app atual e um MVP tecnico de agenda local.

Ele ainda nao e um SaaS completo porque falta:

- autenticacao;
- contas;
- permissoes;
- sincronizacao;
- backup;
- billing real;
- separacao admin/profissional;
- experiencia visual polida.

Mas a base esta correta para evoluir em etapas.

## Melhorias Prioritarias

### Prioridade 1 - Clareza De Produto

- Renomear textos para explicar melhor empresa, equipe, clientes e atendimentos.
- Criar uma tela inicial mais guiada.
- Mostrar cards de status: agenda hoje, profissionais ativos, clientes cadastrados, receita do mes.
- Mostrar proximas acoes: criar servico, cadastrar cliente, cadastrar profissional, criar agendamento.

### Prioridade 2 - Agendamento Profissional

O fluxo correto deve ser:

1. Escolher profissional.
2. Escolher data.
3. Escolher horario livre.
4. Escolher cliente cadastrado ou criar novo rapidamente.
5. Escolher servico cadastrado.
6. Confirmar preco, duracao e observacoes.
7. Salvar.

Regras necessarias:

- bloquear horario ocupado;
- respeitar horario de trabalho do profissional;
- calcular fim do atendimento pela duracao do servico;
- impedir conflito de horario;
- criar receita apenas ao concluir atendimento.

### Prioridade 3 - Ambiente Admin E Profissional

Adicionar modo local:

- `ADMIN`;
- `PROFESSIONAL`.

No modo admin:

- ver tudo.

No modo profissional:

- escolher ou fixar profissional atual;
- filtrar agenda automaticamente;
- esconder financeiro completo;
- esconder premium/configuracoes administrativas.

### Prioridade 4 - Visual

Criar design system interno:

- `AppTopBar`;
- `MetricCard`;
- `SectionHeader`;
- `EmptyState`;
- `FormCard`;
- `AppointmentCard`;
- `StaffChip`;
- `PrimaryActionButton`.

Melhorias visuais:

- reduzir formularios longos;
- usar cards mais densos;
- usar bottom sheets para formularios;
- usar chips para filtros;
- usar estados vazios com acao clara;
- usar labels de status coloridas;
- melhorar espacos e hierarquia;
- evitar telas que parecem exemplos de tutorial.

### Prioridade 5 - Financeiro E Premium

Financeiro:

- resumo do dia;
- resumo do mes;
- receita por profissional;
- receita por servico;
- despesas;
- saldo;
- atendimentos concluidos;
- cancelamentos e faltas.

Premium:

- bloquear relatorios completos;
- bloquear agenda ilimitada apos 30 agendamentos;
- mostrar uso mensal;
- mostrar beneficios reais;
- preparar integracao com Google Play Billing.

## Roadmap Recomendado

### Sprint 1

- Melhorar dashboard.
- Criar componentes visuais reutilizaveis.
- Melhorar onboarding.
- Criar estados vazios.
- Adicionar modo admin/profissional local.

### Sprint 2

- Refazer fluxo de agendamento.
- Selecionar cliente cadastrado.
- Selecionar servico cadastrado.
- Validar conflito de horario.
- Melhorar tela de agenda.

### Sprint 3

- Melhorar financeiro.
- Criar relatorios simples.
- Criar tela premium mais forte.
- Bloquear recursos premium.

### Sprint 4

- Preparar Play Store.
- Politica de privacidade.
- Icone adaptativo.
- Screenshots.
- Teste fechado.
- Google Play Billing.

## Proxima Implementacao Recomendada

A proxima entrega de codigo deve ser:

> Refatorar UX e fluxo de agendamento para usar profissional, cliente e servico cadastrados, com horarios livres reais e bloqueio de conflito.

Essa mudanca aumenta o valor real do app mais do que qualquer melhoria visual isolada.

## Atualizacao Implementada

Depois desta auditoria, o fluxo de agendamento foi evoluido para:

- selecionar profissional cadastrado;
- selecionar cliente cadastrado;
- selecionar servico cadastrado;
- preencher preco e duracao a partir do servico;
- mostrar horarios livres do profissional na data selecionada;
- remover horarios ocupados da lista;
- bloquear conflito de horario no dominio antes de salvar.

## Atualizacao De Agenda

A agenda tambem foi evoluida para:

- exibir uma timeline por horario;
- mostrar slots livres e ocupados;
- destacar status do atendimento;
- exibir resumo do dia por profissional;
- permitir editar/reagendar um atendimento existente;
- permitir concluir ou cancelar direto pela agenda.
