<p align="center">
  <img src="docs/assets/logo.svg" alt="AgendaPro Beauty" width="180" />
</p>

<h1 align="center">AgendaPro Beauty</h1>

<p align="center">
  App Android nativo para agenda, equipe, clientes, servicos e financeiro de barbearias, studios de beleza e profissionais autonomos.
</p>

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-2.3.20-7F52FF?logo=kotlin&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-Nativo-3DDC84?logo=android&logoColor=white">
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-2026.04.01-4285F4?logo=jetpackcompose&logoColor=white">
  <img alt="Material 3" src="https://img.shields.io/badge/Material%203-UI-6750A4?logo=materialdesign&logoColor=white">
  <img alt="Room" src="https://img.shields.io/badge/Room-Database-0F766E">
  <img alt="Build" src="https://img.shields.io/badge/build-passing-brightgreen">
</p>

## Visao Geral

O AgendaPro Beauty e um SaaS mobile local-first para pequenos negocios de beleza. O objetivo e ajudar o dono ou profissional a controlar a rotina de atendimento sem depender de planilhas, papel ou conversas perdidas no WhatsApp.

O app foi desenhado para:

- barbearias;
- saloes pequenos;
- cabeleireiros;
- manicures;
- lash designers;
- designers de sobrancelha;
- profissionais autonomos de beleza.

## Modelo de Produto

O app separa os conceitos principais do negocio:

| Conceito | Significado |
| --- | --- |
| Empresa | Salao, barbearia, studio ou marca do profissional autonomo. |
| Equipe | Profissionais que atendem dentro da empresa. |
| Clientes | Pessoas atendidas pelos profissionais. |
| Servicos | Tabela de servicos com preco e duracao. |
| Agenda | Agendamentos filtrados por data e profissional. |
| Financeiro | Receitas de atendimentos concluidos e despesas simples. |

Essa separacao evita confusao entre "quem e a empresa", "quem atende" e "quem e o cliente".

## Funcionalidades Atuais

- Onboarding da empresa e primeiro profissional.
- Dashboard com visao da empresa.
- Cadastro de equipe/profissionais.
- Cadastro de clientes.
- Cadastro de servicos.
- Agenda diaria.
- Filtro da agenda por profissional.
- Visualizacao de horarios disponiveis por profissional.
- Criacao de agendamento com profissional, cliente cadastrado, servico cadastrado, data e horario livre.
- Bloqueio de conflito de horario por profissional.
- Cancelamento e conclusao de agendamentos.
- Lancamento automatico de receita ao concluir atendimento.
- Financeiro simples com resumo do dia, resumo do mes e despesas manuais.
- Controle base do plano gratis: 30 agendamentos por mes.

## Monetizacao Planejada

Plano gratis:

- ate 30 agendamentos por mes;
- agenda diaria;
- clientes;
- servicos;
- financeiro basico.

Plano premium:

- agendamentos ilimitados;
- relatorios completos;
- backup futuro;
- personalizacao;
- lembretes;
- recursos avancados para equipe.

## Stack

- Kotlin
- Android nativo
- Jetpack Compose
- Material 3
- MVVM
- Clean Architecture simples
- Room Database
- DataStore
- Coroutines + Flow
- Navigation Compose
- Gradle Version Catalog

## Arquitetura

```text
UI Compose
  -> ViewModel
    -> Use Case
      -> Repository Interface
        -> Repository Impl
          -> Room DAO / DataStore
```

Estrutura principal:

```text
app/src/main/java/com/agendaprobeauty/app

core/
  database/
  datastore/
  di/
  navigation/
  ui/theme/
  util/

data/
  local/dao/
  local/entity/
  mapper/
  repository/

domain/
  model/
  repository/
  usecase/

feature/
  agenda/
  appointmentform/
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

O app usa Room como banco local.

Entidades principais:

- `ProfessionalEntity`
- `StaffMemberEntity`
- `ClientEntity`
- `ServiceEntity`
- `AppointmentEntity`
- `FinancialEntryEntity`
- `MonthlyUsageEntity`

## Como Rodar

Pre-requisitos:

- Java 17 ou superior;
- Android SDK instalado;
- Android SDK Platform 36;
- Android Build Tools 36;
- Emulador Android ou celular com depuracao USB.

Neste ambiente, o SDK foi configurado em:

```text
E:/Android/Sdk
```

O arquivo local do projeto deve apontar para o SDK:

```properties
sdk.dir=E:/Android/Sdk
```

Compilar:

```powershell
cd E:\Honeypot
.\gradlew.bat :app:assembleDebug
```

APK gerado:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Rodar No Emulador

Emulador criado:

```text
AgendaPro_Pixel_E
```

Iniciar:

```powershell
$env:ANDROID_HOME="E:\Android\Sdk"
$env:ANDROID_SDK_ROOT="E:\Android\Sdk"
$env:ANDROID_AVD_HOME="E:\Android\Avd"
E:\Android\Sdk\emulator\emulator.exe -avd AgendaPro_Pixel_E -gpu swiftshader_indirect -no-snapshot
```

Instalar o app:

```powershell
E:\Android\Sdk\platform-tools\adb.exe install -r E:\Honeypot\app\build\outputs\apk\debug\app-debug.apk
```

Abrir o app:

```powershell
E:\Android\Sdk\platform-tools\adb.exe shell monkey -p com.agendaprobeauty.app -c android.intent.category.LAUNCHER 1
```

## Roadmap

- Melhorar edicao e reagendamento de atendimentos.
- Editar agendamentos.
- Historico por cliente.
- Relatorios financeiros por periodo.
- Tela premium com bloqueios reais.
- Integracao futura com Google Play Billing.
- Backup e sincronizacao futura.
- Lembretes via notificacao.
- Politica de privacidade e preparacao para Play Store.

## Status

Projeto em MVP tecnico. A base Android, arquitetura, banco local, equipe, agenda e financeiro ja estao funcionando localmente.

## Repositorio

```text
https://github.com/juniorrondini/Honeypot
```
