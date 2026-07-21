package br.com.criptograma.shared.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LetterTest {

    @Test
    void newLetterIsEmpty() {
        assertThat(new Letter('A', null, false, false).isEmpty()).isTrue();
    }

    @Test
    void withGuessAddsGuessWithoutConfirming() {
        Letter letter = new Letter('A', null, false, false).withGuess('Z');
        assertThat(letter.playerGuess()).isEqualTo('Z');
        assertThat(letter.confirmed()).isFalse();
    }

    @Test
    void confirmLocksLetter() {
        Letter letter = new Letter('A', 'Z', false, false).confirm();
        assertThat(letter.confirmed()).isTrue();
    }

    @Test
    void revealSetsGuessAndConfirmedAndRevealed() {
        Letter letter = new Letter('A', null, false, false).reveal('Z');
        assertThat(letter.playerGuess()).isEqualTo('Z');
        assertThat(letter.confirmed()).isTrue();
        assertThat(letter.revealed()).isTrue();
    }

    @Test
    void clearRemovesGuessAndFlags() {
        Letter letter = new Letter('A', 'Z', false, false).clear();
        assertThat(letter.isEmpty()).isTrue();
        assertThat(letter.confirmed()).isFalse();
        assertThat(letter.revealed()).isFalse();
    }

    @Test
    void rejectsLowercaseCipherChar() {
        assertThatThrownBy(() -> new Letter('a', null, false, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsConfirmedWithoutGuess() {
        assertThatThrownBy(() -> new Letter('A', null, true, false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withGuessNormalizesToUpperCase() {
        Letter letter = new Letter('A', null, false, false).withGuess('z');
        assertThat(letter.playerGuess()).isEqualTo('Z');
    }
}
