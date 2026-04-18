# criptograma-shared

Biblioteca Java pura — coração do sistema **Criptograma**.

Sem dependências de Android ou Firebase. Contém modelos, validadores,
normalizadores e builders reutilizados por `agent`, `functions` e `app`.

## Stack

- Java 21 + Gradle
- Gson 2.10.1
- JUnit 5 + AssertJ (testes)

## Coordenadas Maven

```
groupId:    br.com.criptograma
artifactId: shared
version:    1.0.0
```

## Comandos

```bash
./gradlew test                  # roda todos os testes
./gradlew publishToMavenLocal   # publica em ~/.m2 para consumo dos outros módulos
```

## Organização

```
src/main/java/br/com/criptograma/shared/   ← código-fonte
src/test/java/br/com/criptograma/shared/   ← testes
docs/fases/                                 ← especificação (fase1, fase2)
docs/prompts/                               ← prompts de geração
CLAUDE.md                                   ← contexto para Claude Code
```

Ver [CLAUDE.md](CLAUDE.md) para as regras e convenções do projeto.
