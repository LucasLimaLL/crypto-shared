package br.com.criptograma.shared.normalizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TextNormalizerTest {

    @Test
    void normalizeConvertsToUpperCase() {
        assertThat(TextNormalizer.normalize("hello")).isEqualTo("HELLO");
    }

    @Test
    void normalizeStripsAccents() {
        assertThat(TextNormalizer.normalize("ação")).isEqualTo("ACAO");
    }

    @Test
    void normalizePreservesSpacesAndPunctuation() {
        assertThat(TextNormalizer.normalize("hello, world!")).isEqualTo("HELLO, WORLD!");
    }

    @Test
    void lettersOnlyRemovesNonLetters() {
        assertThat(TextNormalizer.lettersOnly("HELLO, WORLD!")).isEqualTo("HELLOWORLD");
    }

    @Test
    void lettersOnlyStripsAccents() {
        assertThat(TextNormalizer.lettersOnly("café")).isEqualTo("CAFE");
    }

    @Test
    void forDisplayTrimsAndUppercases() {
        assertThat(TextNormalizer.forDisplay("  hello  ")).isEqualTo("HELLO");
    }

    @Test
    void normalizeThrowsForNull() {
        assertThatThrownBy(() -> TextNormalizer.normalize(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void forDisplayThrowsForNull() {
        assertThatThrownBy(() -> TextNormalizer.forDisplay(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
