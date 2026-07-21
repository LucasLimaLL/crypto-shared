package br.com.criptograma.shared.builder;

import br.com.criptograma.shared.model.GameBoard;
import br.com.criptograma.shared.model.Puzzle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameBuilderTest {

    private static Puzzle puzzle(String solution) {
        return PuzzleBuilder.newPuzzle().solution(solution).build();
    }

    @Test
    void boardHasOneLetterPerCipherLetter() {
        Puzzle p = puzzle("HELLO");
        GameBoard board = GameBuilder.from(p);
        assertThat(board.letters()).hasSize(5);
    }

    @Test
    void spacesAreExcludedFromLetterList() {
        Puzzle p = puzzle("HELLO WORLD");
        GameBoard board = GameBuilder.from(p);
        assertThat(board.letters()).hasSize(10);
    }

    @Test
    void allLettersStartEmpty() {
        GameBoard board = GameBuilder.from(puzzle("TEST"));
        assertThat(board.letters()).allMatch(l -> l.isEmpty() && !l.confirmed() && !l.revealed());
    }

    @Test
    void hintsUsedStartsAtZero() {
        assertThat(GameBuilder.from(puzzle("TEST")).hintsUsed()).isZero();
    }

    @Test
    void throwsForNullPuzzle() {
        assertThatThrownBy(() -> GameBuilder.from(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
