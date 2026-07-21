package br.com.criptograma.shared.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationResultTest {

    @Test
    void validIsValid() {
        assertThat(ValidationResult.valid().isValid()).isTrue();
    }

    @Test
    void invalidIsNotValid() {
        assertThat(ValidationResult.invalid("reason").isValid()).isFalse();
    }

    @Test
    void invalidCarriesReason() {
        ValidationResult result = ValidationResult.invalid("something went wrong");
        assertThat(((ValidationResult.Invalid) result).reason()).isEqualTo("something went wrong");
    }

    @Test
    void invalidRejectsBlankReason() {
        assertThatThrownBy(() -> ValidationResult.invalid(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void patternMatchingExhaustive() {
        ValidationResult result = ValidationResult.valid();
        String output = switch (result) {
            case ValidationResult.Valid v -> "ok";
            case ValidationResult.Invalid i -> "fail";
        };
        assertThat(output).isEqualTo("ok");
    }
}
