# finalize-setup.ps1
# -------------------------------------------------------------------
# Limpa artefatos criados pelo sandbox Cowork (index.lock orfao,
# test.txt, .test-file), garante branches develop e
# feature/projeto-criptograma-puzzle, e commita a estrutura base
# do modulo criptograma-shared.
#
# USO (no PowerShell, na raiz do repo):
#     .\scripts\finalize-setup.ps1
# -------------------------------------------------------------------

$ErrorActionPreference = 'Stop'

$repo = (Resolve-Path "$PSScriptRoot\..").Path
Write-Host "Repo: $repo" -ForegroundColor Cyan
Set-Location $repo

# 1) Remover lock orfao do index, se existir
$lock = Join-Path $repo '.git\index.lock'
if (Test-Path $lock) {
    Write-Host "Removendo .git\index.lock orfao..." -ForegroundColor Yellow
    Remove-Item -Force $lock
}

# 2) Remover refs .lock orfaos (develop.lock, HEAD.lock, etc.)
Get-ChildItem -Path "$repo\.git" -Recurse -Filter '*.lock' -ErrorAction SilentlyContinue |
    ForEach-Object {
        Write-Host "Removendo $($_.FullName)" -ForegroundColor Yellow
        Remove-Item -Force $_.FullName
    }

# 3) Remover arquivos de teste criados pelo sandbox
foreach ($f in @('test.txt', '.test-file')) {
    $p = Join-Path $repo $f
    if (Test-Path $p) {
        Write-Host "Removendo $f" -ForegroundColor Yellow
        Remove-Item -Force $p
    }
}

# 4) Normalizar line-endings do .gitignore (o sandbox pode ter tocado nele)
git config core.autocrlf true | Out-Null

# 5) Garantir identidade do commiter (usa config global se ja setada)
$userName  = git config user.name
$userEmail = git config user.email
if (-not $userName)  { git config user.name  'Lucas Lima' }
if (-not $userEmail) { git config user.email 'lucaslima58227@gmail.com' }

# 6) Garantir branch develop (a partir de main)
git show-ref --verify --quiet refs/heads/develop
if ($LASTEXITCODE -ne 0) {
    Write-Host "Criando branch develop a partir de main..." -ForegroundColor Green
    git branch develop main
}

# 7) Trocar para develop e garantir que esta na ponta de main
git checkout develop
git reset --hard main

# 8) Garantir branch feature/projeto-criptograma-puzzle a partir de develop
git show-ref --verify --quiet refs/heads/feature/projeto-criptograma-puzzle
if ($LASTEXITCODE -ne 0) {
    Write-Host "Criando feature/projeto-criptograma-puzzle a partir de develop..." -ForegroundColor Green
    git branch feature/projeto-criptograma-puzzle develop
}

# 9) Trocar para a feature branch para commit do scaffold
git checkout feature/projeto-criptograma-puzzle

# 10) Restaurar .gitignore (caso o sandbox tenha gerado diff de CRLF)
git checkout -- .gitignore 2>$null

# 11) Stage + commit
git add -A
git status --short

$changes = git diff --cached --name-only
if ($changes) {
    git commit -m "chore(scaffold): estrutura base Gradle/Java21 do criptograma-shared

- build.gradle (java-library + maven-publish, Java 21, Gson 2.10.1, JUnit 5)
- settings.gradle (rootProject.name = criptograma-shared)
- CLAUDE.md (base de conhecimento para Claude Code)
- README.md atualizado
- arvore de pacotes br.com.criptograma.shared (src/main + src/test)
- docs/fases e docs/prompts com .gitkeep"
} else {
    Write-Host "Nada para commitar." -ForegroundColor Yellow
}

# 12) Push (opcional — descomente quando quiser subir)
# git push -u origin develop
# git push -u origin feature/projeto-criptograma-puzzle

Write-Host ""
Write-Host "Branches atuais:" -ForegroundColor Cyan
git branch
Write-Host ""
Write-Host "Pronto. Para subir ao remoto:" -ForegroundColor Cyan
Write-Host "    git push -u origin develop"
Write-Host "    git push -u origin feature/projeto-criptograma-puzzle"
