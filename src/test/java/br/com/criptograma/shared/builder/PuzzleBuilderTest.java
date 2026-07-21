package br.com.criptograma.shared.builder;

import br.com.criptograma.shared.enums.DifficultyLevel;
import br.com.criptograma.shared.model.Puzzle;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PuzzleBuilderTest {

    @Test
    void buildWithSolutionGeneratesCipherText() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("HELLO").build();
        assertThat(puzzle.cipherText()).isNotBlank();
        assertThat(puzzle.cipherText()).isNotEqualTo(puzzle.solution());
    }

    @Test
    void buildNormalizesSolutionToUpperCase() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("hello world").build();
        assertThat(puzzle.solution()).isEqualTo("HELLO WORLD");
    }

    @Test
    void buildAssignsRandomIdWhenNotProvided() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("TEST").build();
        assertThat(puzzle.id()).isNotBlank();
    }

    @Test
    void buildUsesProvidedId() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().id("p1").solution("TEST").build();
        assertThat(puzzle.id()).isEqualTo("p1");
    }

    @Test
    void buildDefaultsDifficultyToMedium() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("TEST").build();
        assertThat(puzzle.difficulty()).isEqualTo(DifficultyLevel.MEDIUM);
    }

    @Test
    void buildUsesProvidedDifficulty() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("TEST").difficulty(DifficultyLevel.HARD).build();
        assertThat(puzzle.difficulty()).isEqualTo(DifficultyLevel.HARD);
    }

    @Test
    void buildStoresHint() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("TEST").hint("think twice").build();
        assertThat(puzzle.hint()).isEqualTo("think twice");
    }

    @Test
    void cipherTextHasSameLengthAsSolution() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("HELLO WORLD").build();
        assertThat(puzzle.cipherText().length()).isEqualTo(puzzle.solution().length());
    }

    @Test
    void buildThrowsWhenSolutionIsNull() {
        assertThatThrownBy(() -> PuzzleBuilder.newPuzzle().build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void buildThrowsWhenSolutionIsBlank() {
        assertThatThrownBy(() -> PuzzleBuilder.newPuzzle().solution("  ").build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cipherMapIsConsistentWithCipherText() {
        Puzzle puzzle = PuzzleBuilder.newPuzzle().solution("HELLO").build();
        for (int i = 0; i < puzzle.solution().length(); i++) {
            char plain = puzzle.solution().charAt(i);
            char cipher = puzzle.cipherText().charAt(i);
            if (Character.isLetter(plain)) {
                assertThat(puzzle.cipherMap().decode(cipher)).isEqualTo(plain);
            }
        }
    }
}
