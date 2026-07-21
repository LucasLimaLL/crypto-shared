package br.com.criptograma.shared.validator;

import br.com.criptograma.shared.model.Puzzle;
import br.com.criptograma.shared.normalizer.TextNormalizer;

public final class PuzzleValidator {

    private PuzzleValidator() {
    }

    public static ValidationResult validate(Puzzle puzzle) {
        if (puzzle == null) {
            return ValidationResult.invalid("puzzle must not be null");
        }
        String normalizedSolution = TextNormalizer.lettersOnly(puzzle.solution());
        if (normalizedSolution.length() < 3) {
            return ValidationResult.invalid("solution must contain at least 3 letters");
        }
        String normalizedCipher = TextNormalizer.lettersOnly(puzzle.cipherText());
        if (normalizedCipher.length() != normalizedSolution.length()) {
            return ValidationResult.invalid(
                    "cipherText and solution must have the same number of letters");
        }
        for (char c : normalizedSolution.toCharArray()) {
            if (!puzzle.cipherMap().containsCipher(puzzle.cipherMap().encode(c))) {
                return ValidationResult.invalid("solution contains letter not covered by cipherMap: " + c);
            }
        }
        return ValidationResult.valid();
    }
}
