#!/usr/bin/env bash
# -------------------------------------------------------------------
# Limpa artefatos criados pelo sandbox Cowork (index.lock orfao,
# test.txt, .test-file), garante branches develop e
# feature/projeto-criptograma-puzzle, e commita a estrutura base
# do modulo criptograma-shared.
#
# USO (no Git Bash, na raiz do repo):
#     ./scripts/finalize-setup.sh
# -------------------------------------------------------------------
set -euo pipefail

# Ir para a raiz do repo (um nivel acima do diretorio do script)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$REPO_ROOT"
echo ">> Repo: $REPO_ROOT"

# 1) Remover locks orfaos do git
echo ">> Limpando locks orfaos..."
find .git -maxdepth 3 -name '*.lock' -type f -print -delete 2>/dev/null || true

# 2) Remover arquivos de teste criados pelo sandbox
for f in test.txt .test-file; do
    if [ -e "$f" ]; then
        echo ">> Removendo $f"
        rm -f "$f"
    fi
done

# 3) Normalizar line-endings (autocrlf true para Windows)
git config core.autocrlf true

# 4) Garantir identidade do commiter
if [ -z "$(git config user.name  || true)" ]; then git config user.name  'Lucas Lima'; fi
if [ -z "$(git config user.email || true)" ]; then git config user.email 'lucaslima58227@gmail.com'; fi

# 5) Garantir branch develop (a partir de main)
if ! git show-ref --verify --quiet refs/heads/develop; then
    echo ">> Criando branch develop a partir de main..."
    git branch develop main
fi

# 6) Garantir branch feature/projeto-criptograma-puzzle a partir de develop
if ! git show-ref --verify --quiet refs/heads/feature/projeto-criptograma-puzzle; then
    echo ">> Criando feature/projeto-criptograma-puzzle a partir de develop..."
    git branch feature/projeto-criptograma-puzzle develop
fi

# 7) Trocar para a feature branch para commit do scaffold
git checkout feature/projeto-criptograma-puzzle

# 8) Restaurar .gitignore se o sandbox gerou diff apenas de CRLF
git checkout -- .gitignore 2>/dev/null || true

# 9) Stage + commit
git add -A
git status --short

if ! git diff --cached --quiet; then
    git commit -m "chore(scaffold): estrutura base Gradle/Java21 do criptograma-shared

- build.gradle (java-library + maven-publish, Java 21, Gson 2.10.1, JUnit 5)
- settings.gradle (rootProject.name = criptograma-shared)
- CLAUDE.md (base de conhecimento para Claude Code)
- README.md atualizado
- arvore de pacotes br.com.criptograma.shared (src/main + src/test)
- docs/fases e docs/prompts com .gitkeep
- scripts/finalize-setup (ps1 + sh) para cleanup manual"
else
    echo ">> Nada para commitar."
fi

# 10) Push (descomente quando quiser subir)
# git push -u origin develop
# git push -u origin feature/projeto-criptograma-puzzle

echo ""
echo ">> Branches:"
git branch
echo ""
echo ">> Pronto. Para subir ao remoto:"
echo "    git push -u origin develop"
echo "    git push -u origin feature/projeto-criptograma-puzzle"
