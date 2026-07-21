# criptograma-shared — Base de Conhecimento

> Lido automaticamente pelo Claude Code em toda sessão.

## O que é este projeto

Biblioteca Java pura — coração do sistema Criptograma.
Sem dependências de Android ou Firebase.

**Especificações:** docs/fases/criptograma-fase1.md + docs/fases/criptograma-fase2.md
**Prompt de geração:** docs/prompts/prompt-shared.md

## Stack

    Java 25 + Gradle
    Gson 2.10.1
    JUnit 5 + AssertJ (testes)
    Checkstyle 10.21.4
    Jacoco 0.8.12
    SonarQube CE (self-hosted — http://localhost:9000, projeto: criptograma-shared)
    Zero dependências de android.* ou com.google.firebase.*

## Coordenadas Maven

    groupId:    br.com.criptograma
    artifactId: shared
    version:    1.0.0

## Regras obrigatórias

    → Zero android.* ou com.google.firebase.*
    → Toda lógica pura — sem I/O, sem rede
    → Java 25: sealed classes, records, pattern matching
    → Zero comentários: nem //, nem /* */, nem Javadoc — código se autodocumenta
    → Checkstyle falha o build se houver comentário em qualquer .java
    → Testes para toda lógica pública
    → Cobertura mínima: 97% INSTRUCTION / LINE / METHOD / CLASS; 90% BRANCH

## Comandos

    ./gradlew test                              ← roda testes + jacocoTestReport
    ./gradlew jacocoTestCoverageVerification    ← verifica thresholds de cobertura
    ./gradlew checkstyleMain                    ← lint
    ./gradlew publishToMavenLocal               ← publica para os outros módulos
    SONAR_TOKEN=<token> ./gradlew sonar         ← análise SonarQube

## Convenção de commits

    Escopos: model | builder | validator | normalizer | enums | infra | test

    feat(builder): implementar GameBuilder com cobertura de letras
    test(validator): cobrir caso de placeholder (?) nas dicas
    fix(normalizer): remover hífen composto ao normalizar
