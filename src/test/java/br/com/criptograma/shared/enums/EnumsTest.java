package br.com.criptograma.shared.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnumsTest {

    @Test
    void difficultyLevelHasThreeValues() {
        assertThat(DifficultyLevel.values()).containsExactly(
                DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.HARD);
    }

    @Test
    void gameModeHasThreeValues() {
        assertThat(GameMode.values()).containsExactly(
                GameMode.CLASSIC, GameMode.TIMED, GameMode.THEME);
    }
}
