package br.com.criptograma.shared.validator;

import br.com.criptograma.shared.builder.GameBuilder;
import br.com.criptograma.shared.builder.PuzzleBuilder;
import br.com.criptograma.shared.model.GameBoard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GuessValidatorTest {

    private static GameBoard board() {
        return GameBuilder.from(PuzzleBuilder.newPuzzle().solution("HELLO").build());
    }

    @Test
    void validGuessIsAccepted() {
        assertThat(GuessValidator.validate('A', 0, board()).isValid()).isTrue();
    }

    @Test
    void nullBoardIsRejected() {
        assertThat(GuessValidator.validate('A', 0, null).isValid()).isFalse();
    }

    @Test
    void digitGuessIsRejected() {
        assertThat(GuessValidator.validate('1', 0, board()).isValid()).isFalse();
    }

    @Test
    void negativeIndexIsRejected() {
        assertThat(GuessValidator.validate('A', -1, board()).isValid()).isFalse();
    }

    @Test
    void outOfBoundsIndexIsRejected() {
        GameBoard b = board();
        assertThat(GuessValidator.validate('A', b.letters().size(), b).isValid()).isFalse();
    }

    @Test
    void confirmedLetterIsRejected() {
        GameBoard b = board();
        b = b.withLetterAt(0, b.letters().get(0).withGuess('A').confirm());
        assertThat(GuessValidator.validate('B', 0, b).isValid()).isFalse();
    }

    @Test
    void revealedLetterIsRejected() {
        GameBoard b = board();
        b = b.withLetterAt(0, b.letters().get(0).reveal('Z'));
        assertThat(GuessValidator.validate('B', 0, b).isValid()).isFalse();
    }

    @Test
    void credentialValidForMinimumLength() {
        assertThat(GuessValidator.validateCredential("abc", "username").isValid()).isTrue();
    }

    @Test
    void credentialRejectedWhenBlank() {
        assertThat(GuessValidator.validateCredential("  ", "username").isValid()).isFalse();
    }

    @Test
    void credentialRejectedWhenTooShort() {
        assertThat(GuessValidator.validateCredential("ab", "username").isValid()).isFalse();
    }
}
