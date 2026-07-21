package br.com.criptograma.shared.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CipherMapTest {

    private static CipherMap simpleMap() {
        return CipherMap.of(Map.of('A', 'Z', 'B', 'Y', 'C', 'X'));
    }

    @Test
    void decodesCorrectly() {
        assertThat(simpleMap().decode('A')).isEqualTo('Z');
    }

    @Test
    void encodesCorrectly() {
        assertThat(simpleMap().encode('Z')).isEqualTo('A');
    }

    @Test
    void containsCipherReturnsTrueForMappedKey() {
        assertThat(simpleMap().containsCipher('B')).isTrue();
    }

    @Test
    void containsCipherReturnsFalseForUnmappedKey() {
        assertThat(simpleMap().containsCipher('D')).isFalse();
    }

    @Test
    void sizeReflectsNumberOfMappings() {
        assertThat(simpleMap().size()).isEqualTo(3);
    }

    @Test
    void rejectsNullMapping() {
        assertThatThrownBy(() -> CipherMap.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsLowercaseCipherKey() {
        assertThatThrownBy(() -> CipherMap.of(Map.of('a', 'Z')))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNonBijectiveMapping() {
        assertThatThrownBy(() -> CipherMap.of(Map.of('A', 'Z', 'B', 'Z')))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bijective");
    }

    @Test
    void decodeThrowsForUnknownCipherChar() {
        assertThatThrownBy(() -> simpleMap().decode('Z'))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void equalMapsAreEqual() {
        assertThat(simpleMap()).isEqualTo(simpleMap());
    }

    @Test
    void asMapReturnsUnmodifiableView() {
        assertThatThrownBy(() -> simpleMap().asMap().put('D', 'W'))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
