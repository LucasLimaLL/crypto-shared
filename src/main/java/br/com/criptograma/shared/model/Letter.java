package br.com.criptograma.shared.model;

public record Letter(char cipherChar, Character playerGuess, boolean confirmed, boolean revealed) {

    public Letter {
        if (!Character.isLetter(cipherChar) || !Character.isUpperCase(cipherChar)) {
            throw new IllegalArgumentException("cipherChar must be an uppercase letter: " + cipherChar);
        }
        if (playerGuess != null && (!Character.isLetter(playerGuess) || !Character.isUpperCase(playerGuess))) {
            throw new IllegalArgumentException("playerGuess must be an uppercase letter: " + playerGuess);
        }
        if (confirmed && playerGuess == null) {
            throw new IllegalArgumentException("confirmed letter must have a playerGuess");
        }
    }

    public boolean isEmpty() {
        return playerGuess == null;
    }

    public Letter withGuess(char guess) {
        return new Letter(cipherChar, Character.toUpperCase(guess), false, revealed);
    }

    public Letter confirm() {
        return new Letter(cipherChar, playerGuess, true, revealed);
    }

    public Letter reveal(char plainChar) {
        return new Letter(cipherChar, Character.toUpperCase(plainChar), true, true);
    }

    public Letter clear() {
        return new Letter(cipherChar, null, false, false);
    }
}
