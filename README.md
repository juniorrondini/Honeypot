# AgendaPro Beauty

Aplicativo Android nativo em Kotlin para agenda, clientes, serviços e financeiro simples de profissionais autônomos de beleza.

Modelo de produto:

- `Empresa`: salão, barbearia, studio ou profissional autônomo com marca própria.
- `Equipe`: profissionais que atendem dentro da empresa.
- `Clientes`: pessoas atendidas pelos profissionais.
- `Agenda`: sempre pode ser filtrada por profissional para mostrar horários livres.

Stack:

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Clean Architecture simples
- Room
- DataStore
- Coroutines + Flow
- Navigation Compose

## Rodar localmente

1. Instale Android Studio Otter 3 Feature Drop ou mais recente.
2. Instale Android SDK com plataforma 36 e Build Tools 36.
3. Crie `local.properties` apontando para o SDK, por exemplo:

```properties
sdk.dir=C\:\\Users\\SEU_USUARIO\\AppData\\Local\\Android\\Sdk
```

4. Execute:

```powershell
.\gradlew.bat :app:assembleDebug
```
