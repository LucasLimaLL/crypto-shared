package br.com.criptograma.shared.model;

import br.com.criptograma.shared.builder.GameBuilder;
import br.com.criptograma.shared.builder.PuzzleBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameBoardTest {

    private static Puzzle anyPuzzle() {
        return PuzzleBuilder.newPuzzle().solution("HELLO").build();
    }

    @Test
    void newBoardIsNotSolved() {
        assertThat(GameBuilder.from(anyPuzzle()).isSolved()).isFalse();
    }

    @Test
    void confirmedCountStartsAtZero() {
        assertThat(GameBuilder.from(anyPuzzle()).confirmedCount()).isZero();
    }

    @Test
    void withLetterAtReplacesLetterAtIndex() {
        GameBoard board = GameBuilder.from(anyPuzzle());
        Letter updated = board.letters().get(0).withGuess('A');
        GameBoard next = board.withLetterAt(0, updated);
        assertThat(next.letters().get(0).playerGuess()).isEqualTo('A');
    }

    @Test
    void withHintUsedIncrementsCounter() {
        GameBoard board = GameBuilder.from(anyPuzzle()).withHintUsed();
        assertThat(board.hintsUsed()).isEqualTo(1);
    }

    @Test
    void isSolvedWhenAllLettersConfirmed() {
        GameBoard board = GameBuilder.from(anyPuzzle());
        for (int i = 0; i < board.letters().size(); i++) {
            Letter revealed = board.letters().get(i).reveal(board.puzzle().cipherMap().decode(
                    board.letters().get(i).cipherChar()));
            board = board.withLetterAt(i, revealed);
        }
        assertThat(board.isSolved()).isTrue();
    }

    @Test
    void withLetterAtThrowsForInvalidIndex() {
        GameBoard board = GameBuilder.from(anyPuzzle());
        assertThatThrownBy(() -> board.withLetterAt(-1, board.letters().get(0)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void lettersListIsUnmodifiable() {
        GameBoard board = GameBuilder.from(anyPuzzle());
        assertThatThrownBy(() -> board.letters().add(new Letter('Z', null, false, false)))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
