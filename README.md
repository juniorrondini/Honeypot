<p align="center">
  <img src="docs/assets/logo.svg" alt="AgendaPro Beauty" width="180" />
</p>

<h1 align="center">AgendaPro Beauty</h1>

<p align="center">
  SaaS mobile Android para agenda, equipe, clientes, servicos, financeiro e monetizacao premium em pequenos negocios de beleza.
</p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?logo=kotlin&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-Nativo-3DDC84?logo=android&logoColor=white">
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-2026.04.01-4285F4?logo=jetpackcompose&logoColor=white">
  <img alt="Material 3" src="https://img.shields.io/badge/Material%203-UI-6750A4?logo=materialdesign&logoColor=white">
  <img alt="Room" src="https://img.shields.io/badge/Room-2.8.4-0F766E">
  <img alt="DataStore" src="https://img.shields.io/badge/DataStore-1.2.1-2563EB">
  <img alt="Min SDK" src="https://img.shields.io/badge/minSdk-26-111827">
  <img alt="Status" src="https://img.shields.io/badge/status-MVP%20local--first-brightgreen">
</p>

## Sumario

- [Visao Geral](#visao-geral)
- [Problema e Proposta](#problema-e-proposta)
- [Publico-Alvo](#publico-alvo)
- [Modelo de Produto](#modelo-de-produto)
- [Funcionalidades](#funcionalidades)
- [Dados de Demonstracao](#dados-de-demonstracao)
- [Monetizacao](#monetizacao)
- [Stack Tecnica](#stack-tecnica)
- [Arquitetura](#arquitetura)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Banco Local](#banco-local)
- [Como Rodar](#como-rodar)
- [Testar no Emulador](#testar-no-emulador)
- [Checklist de QA](#checklist-de-qa)
- [Preparacao para Play Store](#preparacao-para-play-store)
- [Roadmap](#roadmap)
- [Status](#status)

## Visao Geral

O AgendaPro Beauty e um aplicativo Android nativo, local-first, pensado para barbeiros, cabeleireiros, manicures, lash designers, designers de sobrancelha e pequenos saloes que precisam controlar agenda e operacao sem depender de papel, planilhas ou mensagens perdidas no WhatsApp.

O objetivo do produto e evoluir para um SaaS mobile com assinatura premium. A versao atual ja entrega um MVP funcional com cadastro de empresa, profissionais, clientes, servicos, agenda diaria, financeiro basico, modo de usuario e controle de limite gratis.

## Problema e Proposta

Pequenos negocios de beleza normalmente sofrem com:

- agendamentos espalhados em conversas;
- conflito de horario entre profissionais;
- falta de historico de clientes;
- dificuldade para saber quanto entrou no dia ou no mes;
- baixa disciplina para controlar despesas;
- pouca clareza sobre quais clientes e servicos geram receita.

A proposta do AgendaPro Beauty e centralizar esses fluxos em uma experiencia simples:

| Dor | Solucao no app |
| --- | --- |
| Agenda baguncada | Agenda diaria com horarios disponiveis por profissional. |
| Conflito de horario | Bloqueio de sobreposicao para o mesmo profissional. |
| Cliente sem historico | Detalhe do cliente com atendimentos e total gasto. |
| Servicos sem padrao | Catalogo com preco e duracao. |
| Caixa confuso | Receita automatica ao concluir atendimento e despesas manuais. |
| Produto gratuito sem limite | Plano gratis com ate 30 agendamentos por mes. |

## Publico-Alvo

- Barbearias pequenas.
- Saloes de bairro.
- Cabeleireiros independentes.
- Manicures.
- Lash designers.
- Designers de sobrancelha.
- Profissionais autonomos que atendem por agenda.
- Studios com 2 a 6 profissionais.

## Modelo de Produto

O app separa claramente os conceitos centrais do negocio:

| Conceito | Significado |
| --- | --- |
| Empresa | Salao, barbearia, studio ou marca do profissional autonomo. |
| Profissional | Pessoa da equipe que executa atendimentos. |
| Cliente | Pessoa atendida pelos profissionais. |
| Servico | Item vendido, com preco e duracao. |
| Agenda | Atendimentos marcados por data, horario e profissional. |
| Financeiro | Receitas concluidas, despesas e saldo estimado. |
| Plano | Regras de uso gratis ou premium. |

Essa divisao evita a confusao comum entre "quem e a empresa", "quem atende" e "quem e o cliente".

## Funcionalidades

### Empresa e Onboarding

- Configuracao inicial da empresa.
- Cadastro do primeiro profissional.
- Persistencia do onboarding via DataStore.
- Criacao automatica de dados de demonstracao para acelerar testes.

### Dashboard

- Visao geral do negocio.
- Receita do dia.
- Receita do mes.
- Contagem de agendamentos do dia.
- Atendimentos concluidos.
- Quantidade de clientes, equipe e servicos.
- Lista de proximos atendimentos.
- Aviso de limite gratis proximo do fim.
- Acesso rapido para agenda, equipe, financeiro e premium.

### Equipe e Profissionais

- Cadastro de profissionais.
- Edicao de nome, funcao, telefone, horario inicial, horario final e tamanho do slot.
- Desativacao de profissionais.
- Janela de trabalho usada no calculo de horarios livres.

### Clientes

- Cadastro de clientes.
- Busca por nome.
- Edicao de nome, telefone e observacoes.
- Detalhe do cliente.
- Historico de atendimentos.
- Total gasto pelo cliente.
- Ultima visita.
- Agendamento rapido a partir do cliente.

### Servicos

- Cadastro de servicos.
- Edicao de nome, preco e duracao.
- Desativacao de servicos.
- Uso dos servicos no calculo de duracao do atendimento.

### Agenda

- Agenda diaria.
- Filtro por profissional.
- Timeline visual por horario.
- Visualizacao de horarios livres.
- Criacao de atendimento com profissional, cliente, servico, data e horario.
- Edicao e reagendamento basico.
- Cancelamento.
- Conclusao de atendimento.
- Bloqueio de conflito de horario para o mesmo profissional.

### Financeiro

- Receita automatica ao concluir atendimento.
- Despesas manuais.
- Resumo do dia.
- Resumo do mes.
- Receita por servico.
- Despesas por categoria.
- Movimentacoes do mes.
- Saldo estimado.

### Planos e Premium

- Plano gratis limitado a 30 agendamentos por mes.
- Bloqueio real de novos agendamentos ao atingir o limite gratis.
- Tela Premium com estado local.
- Ativacao local do Premium para teste.
- Retorno ao plano gratis para validar bloqueios.

### Modo de Usuario

- Modo Administrador.
- Modo Profissional.
- Navegacao simplificada no modo Profissional.
- Bloqueio de permanencia em rotas administrativas quando o modo Profissional esta ativo.

## Dados de Demonstracao

Ao abrir o app em uma instalacao nova, o projeto cria automaticamente uma base de teste local:

| Tipo | Dados |
| --- | --- |
| Empresa | Honeypot Beauty Studio |
| Profissionais | Lucas Andrade, Marina Costa, Bianca Lima |
| Servicos | Corte masculino, Barba, Corte + barba, Escova, Manicure |
| Clientes | Rafael Souza, Camila Nunes, Thiago Martins, Juliana Rocha |

O seed e idempotente:

- roda apenas uma vez por instalacao;
- nao duplica nomes existentes;
- marca o onboarding como concluido;
- permite testar o produto imediatamente.

Para resetar os dados no emulador:

```powershell
E:\Android\Sdk\platform-tools\adb.exe uninstall com.agendaprobeauty.app
```

Depois instale o APK novamente.

## Monetizacao

### Plano Gratis

- Ate 30 agendamentos por mes.
- Agenda diaria.
- Cadastro de clientes.
- Cadastro de servicos.
- Financeiro basico.
- Uso local-first.

### Plano Premium

- Agendamentos ilimitados.
- Relatorios completos.
- Backup em nuvem futuro.
- Personalizacao da empresa.
- Lembretes futuros.
- Recursos avancados de equipe.

### Proxima etapa de monetizacao

A tela Premium atual usa estado local para validar a regra de produto. A evolucao natural e integrar Google Play Billing para assinatura real, validar recibos e persistir o entitlement premium com seguranca.

## Stack Tecnica

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Kotlin 2.3.20 |
| Plataforma | Android nativo |
| UI | Jetpack Compose + Material 3 |
| Arquitetura | MVVM + Clean Architecture simples |
| Navegacao | Navigation Compose |
| Banco local | Room 2.8.4 |
| Preferencias | DataStore 1.2.1 |
| Assincrono | Coroutines + Flow |
| Build | Gradle + Version Catalog |
| Min SDK | 26 |
| Target SDK | 36 |

## Arquitetura

Fluxo principal:

```text
Compose Screen
  -> ViewModel
    -> Use Case
      -> Repository Interface
        -> Repository Implementation
          -> Room DAO / DataStore
```

Principios aplicados:

- UI nao acessa Room diretamente.
- ViewModels orquestram estado de tela.
- Use cases concentram regras de aplicacao.
- Repositories isolam fonte de dados.
- Modelos de dominio ficam separados de entidades Room.
- DataStore guarda preferencias e estado de plano/modo.

## Estrutura de Pastas

```text
app/src/main/java/com/agendaprobeauty/app

core/
  database/      Configuracao Room e converters
  datastore/     Preferencias locais
  di/            Injecao manual simples
  navigation/    Rotas e NavHost
  ui/theme/      Tema Material 3
  util/          Datas e dinheiro

data/
  local/dao/     DAOs Room
  local/entity/  Entidades persistidas
  mapper/        Conversao entity <-> domain
  repository/    Implementacoes dos repositories

domain/
  model/         Modelos de negocio
  repository/    Contratos de dados
  usecase/       Regras de aplicacao

feature/
  agenda/
  appointmentedit/
  appointmentform/
  clientdetail/
  clients/
  dashboard/
  finance/
  onboarding/
  premium/
  services/
  settings/
  staff/
```

## Banco Local

O app usa Room como armazenamento local.

Entidades principais:

| Entidade | Responsabilidade |
| --- | --- |
| `ProfessionalEntity` | Empresa/perfil inicial. |
| `StaffMemberEntity` | Profissionais da equipe e disponibilidade base. |
| `ClientEntity` | Clientes atendidos. |
| `ServiceEntity` | Catalogo de servicos. |
| `AppointmentEntity` | Agendamentos e status. |
| `FinancialEntryEntity` | Receitas e despesas. |
| `MonthlyUsageEntity` | Uso mensal planejado para regras de plano. |

O banco atual usa `fallbackToDestructiveMigration` para desenvolvimento rapido. Antes de producao, isso deve ser substituido por migrations versionadas.

## Como Rodar

### Pre-requisitos

- Java 17 ou superior.
- Android SDK instalado.
- Android SDK Platform 36.
- Android Build Tools 36.
- Emulador Android ou celular com depuracao USB.

Neste ambiente, o SDK esta em:

```text
E:/Android/Sdk
```

O arquivo `local.properties` deve apontar para:

```properties
sdk.dir=E:/Android/Sdk
```

### Compilar

```powershell
cd E:\Honeypot
.\gradlew.bat :app:assembleDebug
```

APK gerado:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Testar no Emulador

Emulador criado neste ambiente:

```text
AgendaPro_Pixel_E
```

Configurar variaveis:

```powershell
$env:ANDROID_HOME="E:\Android\Sdk"
$env:ANDROID_SDK_ROOT="E:\Android\Sdk"
$env:ANDROID_AVD_HOME="E:\Android\Avd"
```

Iniciar emulador:

```powershell
E:\Android\Sdk\emulator\emulator.exe -avd AgendaPro_Pixel_E -gpu swiftshader_indirect -no-snapshot
```

Instalar APK:

```powershell
E:\Android\Sdk\platform-tools\adb.exe install -r E:\Honeypot\app\build\outputs\apk\debug\app-debug.apk
```

Abrir app:

```powershell
E:\Android\Sdk\platform-tools\adb.exe shell monkey -p com.agendaprobeauty.app -c android.intent.category.LAUNCHER 1
```

Limpar instalacao:

```powershell
E:\Android\Sdk\platform-tools\adb.exe uninstall com.agendaprobeauty.app
```

## Checklist de QA

Fluxo recomendado para validar o MVP:

1. Abrir o app em uma instalacao limpa.
2. Confirmar se os dados de demonstracao aparecem.
3. Abrir Dashboard e validar metricas iniciais.
4. Entrar em Equipe e editar horario de um profissional.
5. Entrar em Servicos e editar preco/duracao.
6. Entrar em Clientes e editar telefone/observacoes.
7. Criar um agendamento para cada profissional.
8. Tentar criar dois agendamentos no mesmo horario para o mesmo profissional.
9. Concluir um atendimento.
10. Confirmar se a receita apareceu no Financeiro.
11. Lancar uma despesa manual.
12. Alternar para Modo Profissional em Configuracoes.
13. Validar se abas administrativas somem.
14. Voltar para Modo Administrador.
15. Testar ativacao local do Premium.

## Preparacao para Play Store

Antes de publicar:

- Criar icone launcher final.
- Definir nome comercial final.
- Criar politica de privacidade.
- Remover ou controlar dados de demonstracao em builds de producao.
- Trocar destructive migration por migrations reais.
- Criar build release assinado.
- Configurar versionamento `versionCode` e `versionName`.
- Criar screenshots para Play Store.
- Escrever descricao curta e longa.
- Criar teste fechado.
- Integrar Google Play Billing para assinatura real.
- Validar comportamento offline.

## Roadmap

### Curto Prazo

- Melhorar busca e selecao em agendamento.
- Criar periodo customizado no financeiro.
- Adicionar filtros por status na agenda.
- Criar tela de detalhe completo do atendimento.
- Adicionar testes unitarios para use cases principais.

### Medio Prazo

- Google Play Billing.
- Relatorios por profissional, servico e periodo.
- Segmentacao de clientes inativos e recorrentes.
- Permissoes reais com login.
- Backup e sincronizacao em nuvem.
- Notificacoes locais de lembrete.

### Longo Prazo

- Painel web administrativo.
- Multi-unidade.
- Integracao com WhatsApp.
- Confirmacao automatica de atendimento.
- Programa de fidelidade.
- Analytics de churn e receita recorrente.

## Status

Projeto em MVP funcional local-first.

Ja funciona localmente:

- arquitetura Android nativa em Kotlin;
- Compose com Material 3;
- Room + DataStore;
- agenda por profissional;
- clientes e historico;
- servicos;
- equipe com horarios editaveis;
- financeiro;
- premium local;
- modo Administrador/Profissional;
- dados de demonstracao.

Ultima validacao local:

```powershell
.\gradlew.bat :app:assembleDebug
```

Resultado esperado:

```text
BUILD SUCCESSFUL
```

## Repositorio

```text
https://github.com/juniorrondini/Honeypot
```

## Licenca

Ainda nao definida. Antes de distribuicao publica ou comercial, escolha uma licenca apropriada ou mantenha o projeto como codigo proprietario.
