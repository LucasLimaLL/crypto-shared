package br.com.criptograma.shared.validator;

public sealed interface ValidationResult permits ValidationResult.Valid, ValidationResult.Invalid {

    record Valid() implements ValidationResult {}

    record Invalid(String reason) implements ValidationResult {
        public Invalid {
            if (reason == null || reason.isBlank()) {
                throw new IllegalArgumentException("reason must not be blank");
            }
        }
    }

    static ValidationResult valid() {
        return new Valid();
    }

    static ValidationResult invalid(String reason) {
        return new Invalid(reason);
    }

    default boolean isValid() {
        return this instanceof Valid;
    }
}
