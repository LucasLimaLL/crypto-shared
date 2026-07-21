package br.com.criptograma.shared.validator;

import br.com.criptograma.shared.model.GameBoard;

public final class GuessValidator {

    private GuessValidator() {
    }

    public static ValidationResult validate(char guess, int letterIndex, GameBoard board) {
        if (board == null) {
            return ValidationResult.invalid("board must not be null");
        }
        if (!Character.isLetter(guess)) {
            return ValidationResult.invalid("guess must be a letter: " + guess);
        }
        if (letterIndex < 0 || letterIndex >= board.letters().size()) {
            return ValidationResult.invalid("letterIndex out of bounds: " + letterIndex);
        }
        if (board.letters().get(letterIndex).confirmed()) {
            return ValidationResult.invalid("letter at index " + letterIndex + " is already confirmed");
        }
        if (board.letters().get(letterIndex).revealed()) {
            return ValidationResult.invalid("letter at index " + letterIndex + " was revealed by hint");
        }
        return ValidationResult.valid();
    }

    public static ValidationResult validateCredential(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return ValidationResult.invalid(fieldName + " must not be blank");
        }
        if (value.trim().length() < 3) {
            return ValidationResult.invalid(fieldName + " must be at least 3 characters");
        }
        return ValidationResult.valid();
    }
}
