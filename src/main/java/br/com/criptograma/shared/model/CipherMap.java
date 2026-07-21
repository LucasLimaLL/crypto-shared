package br.com.criptograma.shared.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CipherMap {

    private final Map<Character, Character> cipherToPlain;
    private final Map<Character, Character> plainToCipher;

    private CipherMap(Map<Character, Character> cipherToPlain, Map<Character, Character> plainToCipher) {
        this.cipherToPlain = Collections.unmodifiableMap(new HashMap<>(cipherToPlain));
        this.plainToCipher = Collections.unmodifiableMap(new HashMap<>(plainToCipher));
    }

    public static CipherMap of(Map<Character, Character> mapping) {
        if (mapping == null) {
            throw new IllegalArgumentException("mapping must not be null");
        }
        Map<Character, Character> inverse = new HashMap<>();
        for (Map.Entry<Character, Character> entry : mapping.entrySet()) {
            char cipher = entry.getKey();
            char plain = entry.getValue();
            if (!Character.isLetter(cipher) || !Character.isUpperCase(cipher)) {
                throw new IllegalArgumentException("Cipher key must be an uppercase letter: " + cipher);
            }
            if (!Character.isLetter(plain) || !Character.isUpperCase(plain)) {
                throw new IllegalArgumentException("Plain value must be an uppercase letter: " + plain);
            }
            if (inverse.containsKey(plain)) {
                throw new IllegalArgumentException(
                        "Mapping is not bijective: plain letter '" + plain + "' appears more than once");
            }
            inverse.put(plain, cipher);
        }
        return new CipherMap(mapping, inverse);
    }

    public char decode(char cipher) {
        Character plain = cipherToPlain.get(Character.toUpperCase(cipher));
        if (plain == null) {
            throw new IllegalArgumentException("Cipher character not in map: " + cipher);
        }
        return plain;
    }

    public char encode(char plain) {
        Character cipher = plainToCipher.get(Character.toUpperCase(plain));
        if (cipher == null) {
            throw new IllegalArgumentException("Plain character not in map: " + plain);
        }
        return cipher;
    }

    public boolean containsCipher(char cipher) {
        return cipherToPlain.containsKey(Character.toUpperCase(cipher));
    }

    public int size() {
        return cipherToPlain.size();
    }

    public Map<Character, Character> asMap() {
        return cipherToPlain;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CipherMap other)) {
            return false;
        }
        return cipherToPlain.equals(other.cipherToPlain);
    }

    @Override
    public int hashCode() {
        return cipherToPlain.hashCode();
    }

    @Override
    public String toString() {
        return "CipherMap" + cipherToPlain;
    }
}
