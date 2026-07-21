package br.com.criptograma.shared.normalizer;

import java.text.Normalizer;
import java.util.Locale;

public final class TextNormalizer {

    private TextNormalizer() {
    }

    public static String normalize(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        String upper = text.toUpperCase(Locale.ROOT);
        String decomposed = Normalizer.normalize(upper, Normalizer.Form.NFD);
        return decomposed.replaceAll("\\p{M}", "");
    }

    public static String lettersOnly(String text) {
        return normalize(text).replaceAll("[^A-Z]", "");
    }

    public static String forDisplay(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
        return text.trim().toUpperCase(Locale.ROOT);
    }
}
