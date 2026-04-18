# criptograma-shared — Base de Conhecimento

> Lido automaticamente pelo Claude Code em toda sessão.

## O que é este projeto

Biblioteca Java pura — coração do sistema Criptograma.
Sem dependências de Android ou Firebase.

**Especificações:** docs/fases/criptograma-fase1.md + docs/fases/criptograma-fase2.md
**Prompt de geração:** docs/prompts/prompt-shared.md

## Stack

    Java 21 + Gradle
    Gson 2.10.1
    JUnit 5 + AssertJ (testes)
    Zero dependências de android.* ou com.google.firebase.*

## Coordenadas Maven

    groupId:    br.com.criptograma
    artifactId: shared
    version:    1.0.0

## Regras obrigatórias

    → Zero android.* ou com.google.firebase.*
    → Toda lógica pura — sem I/O, sem rede
    → Java 21: sealed classes, records, pattern matching
    → Javadoc em todas as classes e métodos públicos
    → Testes para toda lógica pública

## Comandos

    ./gradlew test                  ← roda testes
    ./gradlew publishToMavenLocal   ← publica para os outros módulos

## Convenção de commits

    Escopos: model | builder | validator | normalizer | enums

    feat(builder): implementar GameBuilder com cobertura de letras
    test(validator): cobrir caso de placeholder (?) nas dicas
    fix(normalizer): remover hífen composto ao normalizar
