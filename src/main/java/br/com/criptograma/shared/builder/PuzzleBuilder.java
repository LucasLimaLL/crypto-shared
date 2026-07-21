package br.com.criptograma.shared.builder;

import br.com.criptograma.shared.enums.DifficultyLevel;
import br.com.criptograma.shared.model.CipherMap;
import br.com.criptograma.shared.model.Puzzle;
import br.com.criptograma.shared.normalizer.TextNormalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PuzzleBuilder {

    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] ROT13    = "NOPQRSTUVWXYZABCDEFGHIJKLM".toCharArray();

    private String id;
    private String solution;
    private String hint;
    private DifficultyLevel difficulty = DifficultyLevel.MEDIUM;
    private CipherMap cipherMap;

    private PuzzleBuilder() {
    }

    public static PuzzleBuilder newPuzzle() {
        return new PuzzleBuilder();
    }

    public PuzzleBuilder id(String id) {
        this.id = id;
        return this;
    }

    public PuzzleBuilder solution(String solution) {
        this.solution = solution;
        return this;
    }

    public PuzzleBuilder hint(String hint) {
        this.hint = hint;
        return this;
    }

    public PuzzleBuilder difficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public PuzzleBuilder cipherMap(CipherMap cipherMap) {
        this.cipherMap = cipherMap;
        return this;
    }

    public Puzzle build() {
        if (solution == null || solution.isBlank()) {
            throw new IllegalStateException("solution is required");
        }
        String normalized = TextNormalizer.normalize(solution);
        String resolvedId = (id != null && !id.isBlank()) ? id : UUID.randomUUID().toString();
        CipherMap resolvedMap = (cipherMap != null) ? cipherMap : rot13MapFor(normalized);
        String cipherText = applyCipher(normalized, resolvedMap);
        return new Puzzle(resolvedId, cipherText, normalized, hint, difficulty, resolvedMap);
    }

    private static CipherMap rot13MapFor(String normalizedSolution) {
        Map<Character, Character> mapping = new HashMap<>();
        for (char c : normalizedSolution.toCharArray()) {
            if (Character.isLetter(c)) {
                int idx = c - 'A';
                mapping.put(ROT13[idx], ALPHABET[idx]);
            }
        }
        return CipherMap.of(mapping);
    }

    private static String applyCipher(String normalizedSolution, CipherMap map) {
        StringBuilder sb = new StringBuilder();
        for (char c : normalizedSolution.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append(map.encode(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
