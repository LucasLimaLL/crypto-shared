# criptograma-shared

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](http://localhost:9000/dashboard?id=criptograma-shared)
[![Quality Gate](https://img.shields.io/badge/quality%20gate-passing-brightgreen)](http://localhost:9000/dashboard?id=criptograma-shared)
[![Coverage](https://img.shields.io/badge/coverage-97%25-brightgreen)](http://localhost:9000/dashboard?id=criptograma-shared)

Pure Java library — the shared core of the **Criptograma** system. Contains models, validators, normalizers, and builders reused by `crypto-app` and any backend services. No Android or framework dependencies.

---

## Prerequisites

| Tool | Version |
|------|---------|
| JDK | 25 (Temurin or GraalVM) |
| Gradle | 8.x (via wrapper — no install required) |
| Docker | 24+ (for SonarQube) |

---

## Local Setup

```bash
# 1. Clone
git clone https://github.com/LucasLimaLL/crypto-shared.git
cd crypto-shared

# 2. Run tests
./gradlew test

# 3. Publish to local Maven cache (required by crypto-app)
./gradlew publishToMavenLocal

# 4. Run static analysis
./gradlew checkstyleMain

# 5. Run SonarQube analysis (requires SonarQube running at http://localhost:9000)
SONAR_TOKEN=<your-token> ./gradlew sonar
```

---

## Maven Coordinates

```xml
<dependency>
  <groupId>br.com.criptograma</groupId>
  <artifactId>shared</artifactId>
  <version>1.0.0</version>
</dependency>
```

Gradle:
```groovy
implementation 'br.com.criptograma:shared:1.0.0'
```

---

## Project Structure

```
crypto-shared/
├── src/
│   ├── main/java/br/com/criptograma/shared/   # Production code
│   └── test/java/br/com/criptograma/shared/   # Unit + integration tests
├── config/
│   └── checkstyle/                            # Checkstyle rules (no-comments enforced)
├── docs/
│   ├── fases/                                 # Feature specifications
│   └── prompts/                               # Generation prompts
└── CLAUDE.md                                  # Context for Claude Code
```

---

## Architecture

- **Pure Java** — zero Android, Spring, or framework dependencies; the library can run anywhere Java 25 runs.
- **Value objects** — core types are immutable records or final classes.
- **No static mutable state** — all operations are pure functions or builders; safe for concurrent use.
- **Published via `mavenLocal`** — `crypto-app` consumes this library using `mavenLocal()` after running `publishToMavenLocal`.

---

## Quality Gates

| Dimension | Minimum |
|-----------|---------|
| Instruction coverage | 97% |
| Line coverage | 97% |
| Branch coverage | 90% |
| Method coverage | 97% |
| Class coverage | 97% |

Both Jacoco (local, fails build) and SonarQube Quality Gate enforce these thresholds.

---

## Contributing

### Branching

```
main              ← protected
feature/<scope>   ← short-lived feature branches
fix/<scope>       ← bug fix branches
```

### Commit Conventions

```
<type>(<scope>): <imperative description>

Types:  feat | fix | test | refactor | docs
Scopes: shared | model | validator | normalizer | builder | infra | test
```

Examples:
```
feat(validator): add CPF format validation
test(model): cover PuzzleWord equality and hashCode
fix(normalizer): handle accented characters in letter normalization
```

### PR Checklist

- [ ] `./gradlew test` passes locally
- [ ] `./gradlew checkstyleMain` passes (zero warnings)
- [ ] New public API has corresponding unit tests
- [ ] No comments in Java files (`//`, `/* */`, Javadoc)
- [ ] `./gradlew jacocoTestCoverageVerification` passes
- [ ] SonarQube Quality Gate green
- [ ] `./gradlew publishToMavenLocal` tested in consuming project before merge

---

## Best Practices Enforced

- **Zero comments** — Checkstyle blocks `//`, `/* */`, and Javadoc; names and structure carry intent.
- **Records for value objects** — All POJOs and DTOs are Java records; no Lombok, no boilerplate getters/setters/builders.
- **No Lombok** — Records, sealed classes, and static factory methods replace every Lombok annotation; the build has no Lombok dependency.
- **Design patterns by intent** — Value objects use the Value Object pattern; complex construction uses Builder or static factory; sealed hierarchies replace inheritance chains; introduce a pattern only when it reduces coupling.
- **No framework leakage** — `build.gradle` has no Android, Spring, or Guava; keep it pure.
- **Semantic versioning** — patch for fixes, minor for new APIs, major for breaking changes. Update `version` in `build.gradle` on every release.
- **Publish before consuming** — always run `./gradlew publishToMavenLocal` after changes before switching to `crypto-app`.

---

## Trusted Committers

| Name | Role |
|------|------|
| Lucas Lima | Maintainer |

To request Trusted Committer status, open an issue describing your contributions.
