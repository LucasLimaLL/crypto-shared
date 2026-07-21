package br.com.criptograma.shared.model;

import br.com.criptograma.shared.enums.DifficultyLevel;

public record Puzzle(
        String id,
        String cipherText,
        String solution,
        String hint,
        DifficultyLevel difficulty,
        CipherMap cipherMap
) {

    public Puzzle {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if (cipherText == null || cipherText.isBlank()) {
            throw new IllegalArgumentException("cipherText must not be blank");
        }
        if (solution == null || solution.isBlank()) {
            throw new IllegalArgumentException("solution must not be blank");
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("difficulty must not be null");
        }
        if (cipherMap == null) {
            throw new IllegalArgumentException("cipherMap must not be null");
        }
    }
}
