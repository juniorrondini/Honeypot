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
- [Analise Tecnica Atual](#analise-tecnica-atual)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Banco Local](#banco-local)
- [Como Rodar](#como-rodar)
- [Rodar no Mac com Android Studio](#rodar-no-mac-com-android-studio)
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

## Analise Tecnica Atual

O projeto esta em um bom ponto de MVP Android local-first. Ele ja tem as pecas principais de um produto real: persistencia local, navegacao, telas separadas por dominio, use cases para regra de negocio, repositories e modelos de dominio separados das entidades do banco.

### Pontos fortes

- Arquitetura simples e compreensivel.
- Separacao clara entre `data`, `domain`, `feature` e `core`.
- Room usado para dados estruturados.
- DataStore usado para preferencias e estado simples.
- Compose com Material 3.
- Navigation Compose centralizada no `AppNavHost`.
- ViewModels mantendo estado de tela com Flow/StateFlow.
- Seed de dados para testar sem cadastro manual.
- Regra real de limite gratis por quantidade de agendamentos no mes.
- Equipe, clientes, servicos, agenda, financeiro e premium local ja integrados.

### Pontos de atencao

- O app ainda nao tem login real.
- O modo Administrador/Profissional e local, nao e permissao segura por usuario autenticado.
- O banco usa `fallbackToDestructiveMigration`, bom para desenvolvimento, ruim para producao.
- Os dados ainda ficam apenas no aparelho.
- Ainda nao existe Google Play Billing.
- Ainda nao existem testes automatizados suficientes.
- O seed de demonstracao e util para desenvolvimento, mas deve ser controlado em build de producao.

### Proximas decisoes tecnicas importantes

| Area | Decisao recomendada |
| --- | --- |
| Login | Criar usuarios com papeis `OWNER`, `ADMIN`, `STAFF` e futuramente `CLIENT`. |
| Backend | Definir se sera Firebase, Supabase, API propria ou outro backend. |
| Sincronizacao | Planejar conflito offline/online antes de sincronizar agenda. |
| Pagamentos | Integrar Google Play Billing para assinatura premium real. |
| Banco | Trocar destructive migration por migrations versionadas. |
| Testes | Comecar por use cases de agenda, conflito de horario, financeiro e limite gratis. |

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

## Rodar no Mac com Android Studio

Esta secao e bem detalhada de proposito. Ela assume que a pessoa nunca rodou um projeto Android antes.

### O que voce precisa ter no Mac

Voce precisa de:

- Um Mac com internet.
- Android Studio instalado.
- O projeto AgendaPro Beauty baixado no Mac.
- Um emulador Android criado dentro do Android Studio.

### Passo 1: instalar o Android Studio

1. Abra o navegador no Mac.
2. Pesquise por `Android Studio download`.
3. Entre no site oficial de desenvolvedores Android.
4. Baixe o Android Studio para macOS.
5. Abra o arquivo baixado.
6. Arraste o Android Studio para a pasta `Applications`.
7. Abra o Android Studio pela pasta `Applications`.
8. Se o Mac perguntar se voce tem certeza que quer abrir, confirme.

Na primeira abertura, o Android Studio pode perguntar se voce quer importar configuracoes antigas. Se voce nunca usou Android Studio antes, escolha:

```text
Do not import settings
```

Depois continue no assistente inicial.

### Passo 2: instalar o SDK pelo assistente inicial

Quando o Android Studio abrir pela primeira vez:

1. Clique em `Next`.
2. Escolha `Standard`, se aparecer essa opcao.
3. Clique em `Next`.
4. Escolha o tema visual que preferir.
5. Clique em `Next`.
6. Na tela de componentes, deixe marcado:
   - Android SDK
   - Android SDK Platform
   - Android Virtual Device
   - Performance ou Emulator, se aparecer
7. Clique em `Next`.
8. Aceite as licencas.
9. Clique em `Finish`.
10. Espere baixar tudo.

Esse download pode demorar. Nao feche o Android Studio enquanto ele estiver baixando.

### Passo 3: abrir o projeto

Depois que o Android Studio terminar a instalacao inicial:

1. Na tela inicial, clique em `Open`.
2. Escolha a pasta do projeto.
3. A pasta correta e a que contem estes arquivos:

```text
README.md
settings.gradle.kts
build.gradle.kts
gradlew
app/
```

Se o projeto estiver em `Downloads`, por exemplo, selecione a pasta:

```text
Downloads/Honeypot
```

Nao selecione a pasta `app`. Selecione a pasta principal do projeto.

### Passo 4: esperar o Android Studio sincronizar

Quando o projeto abrir, o Android Studio vai sincronizar o Gradle automaticamente.

Voce pode ver mensagens como:

```text
Syncing Gradle
Downloading dependencies
Indexing
```

Espere terminar.

Nao clique em varias coisas enquanto ele sincroniza. A primeira vez pode demorar varios minutos.

Quando estiver pronto, o Android Studio normalmente mostra algo como:

```text
Gradle sync finished
```

Se aparecer um botao `Sync Now`, clique nele.

### Passo 5: conferir se o SDK foi encontrado

O Android Studio normalmente cria o arquivo `local.properties` sozinho.

Esse arquivo nao vai para o Git, porque depende da maquina de cada pessoa.

No Mac, ele costuma ficar assim:

```properties
sdk.dir=/Users/SEU_USUARIO/Library/Android/sdk
```

Voce nao precisa escrever isso manualmente se o Android Studio ja reconheceu o SDK.

Se der erro dizendo que nao encontrou SDK:

1. Clique em `Android Studio`.
2. Clique em `Settings` ou `Preferences`.
3. Abra `Languages & Frameworks`.
4. Abra `Android SDK`.
5. Veja o caminho exibido em `Android SDK Location`.
6. Volte no projeto.
7. Crie ou ajuste o arquivo `local.properties` na raiz do projeto.

Exemplo:

```properties
sdk.dir=/Users/maria/Library/Android/sdk
```

Troque `maria` pelo nome do usuario do Mac.

### Passo 6: criar um emulador Android

Agora voce precisa criar um celular falso dentro do computador.

No Android Studio:

1. Procure no canto direito por `Device Manager`.
2. Se nao aparecer, va em `Tools`.
3. Clique em `Device Manager`.
4. Clique em `Create Device`.
5. Escolha um modelo simples, por exemplo:

```text
Pixel 7
```

ou:

```text
Pixel 6
```

6. Clique em `Next`.
7. Escolha uma imagem Android recente.
8. Se aparecer botao `Download`, clique em `Download`.
9. Aceite a licenca.
10. Espere baixar.
11. Depois do download, clique em `Next`.
12. De um nome para o emulador, por exemplo:

```text
AgendaPro_Mac
```

13. Clique em `Finish`.

### Passo 7: ligar o emulador

No `Device Manager`:

1. Encontre o emulador criado.
2. Clique no botao de play.
3. Espere o Android abrir.

Importante: espere aparecer a tela inicial do Android. Nao tente rodar o app enquanto o emulador ainda esta ligando.

### Passo 8: escolher o app para rodar

Na parte de cima do Android Studio, deve aparecer uma barra com:

- o nome da configuracao, normalmente `app`;
- o nome do emulador, por exemplo `AgendaPro_Mac`;
- um botao de play verde.

Confira:

1. No seletor de configuracao, escolha `app`.
2. No seletor de dispositivo, escolha o emulador.
3. Clique no botao verde de play.

O Android Studio vai:

1. compilar o projeto;
2. instalar o app no emulador;
3. abrir o AgendaPro Beauty.

Na primeira vez pode demorar.

### Passo 9: confirmar se os dados de teste apareceram

Quando o app abrir, ele deve criar automaticamente dados de demonstracao:

| Tipo | Dados esperados |
| --- | --- |
| Empresa | Honeypot Beauty Studio |
| Profissionais | Lucas Andrade, Marina Costa, Bianca Lima |
| Servicos | Corte masculino, Barba, Corte + barba, Escova, Manicure |
| Clientes | Rafael Souza, Camila Nunes, Thiago Martins, Juliana Rocha |

Se o app abrir direto no Dashboard, esta correto.

Se ele abrir no onboarding, tambem da para continuar preenchendo manualmente, mas em uma instalacao limpa o seed deve pular o onboarding.

### Passo 10: testar o fluxo principal

Para testar como usuario:

1. Abra `Equipe`.
2. Veja os profissionais cadastrados.
3. Edite o horario de um profissional.
4. Abra `Servicos`.
5. Veja os servicos cadastrados.
6. Abra `Clientes`.
7. Veja os clientes cadastrados.
8. Abra `Agenda`.
9. Clique para criar um agendamento.
10. Escolha profissional, cliente, servico, data e horario.
11. Salve.
12. Volte para a agenda.
13. Conclua um atendimento.
14. Abra `Financeiro`.
15. Confirme se a receita apareceu.

### Passo 11: limpar o app e recriar dados demo

Se voce mexeu muito e quer voltar para o inicio, desinstale o app do emulador.

Jeito facil:

1. No emulador, encontre o icone do app.
2. Clique e segure.
3. Escolha `Uninstall`.
4. Confirme.
5. Volte no Android Studio.
6. Clique no play verde novamente.

Quando instalar de novo, o app cria os dados de demonstracao novamente.

Jeito pelo Terminal do Mac:

1. Abra o app `Terminal`.
2. Rode:

```bash
cd CAMINHO/DA/PASTA/Honeypot
```

Exemplo:

```bash
cd ~/Downloads/Honeypot
```

3. Rode:

```bash
adb uninstall com.agendaprobeauty.app
```

4. Volte no Android Studio.
5. Clique no play verde.

Se o Terminal disser que `adb` nao existe, use o Android Studio para desinstalar pelo icone do app no emulador. E mais simples.

### Problemas comuns no Mac

#### O Android Studio esta demorando muito

Na primeira vez e normal. Ele baixa Gradle, Kotlin, SDK, emulator e dependencias.

Espere terminar a sincronizacao.

#### Apareceu erro de SDK

Abra:

```text
Android Studio > Settings/Preferences > Languages & Frameworks > Android SDK
```

Confirme se existe um SDK instalado.

#### Nao aparece nenhum emulador

Abra:

```text
Tools > Device Manager
```

Crie um dispositivo virtual.

#### O app nao abriu, mas instalou

No emulador, procure pelo app:

```text
AgendaPro Beauty
```

Toque no icone.

#### Quero rodar sem Android Studio pelo Terminal do Mac

Depois que o Android Studio ja instalou o SDK, voce pode rodar:

```bash
cd ~/Downloads/Honeypot
./gradlew :app:assembleDebug
```

O APK sera gerado em:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Para instalar pelo Terminal, o emulador precisa estar aberto:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell monkey -p com.agendaprobeauty.app -c android.intent.category.LAUNCHER 1
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
