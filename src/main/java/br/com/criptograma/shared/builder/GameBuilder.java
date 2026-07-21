package br.com.criptograma.shared.builder;

import br.com.criptograma.shared.model.GameBoard;
import br.com.criptograma.shared.model.Letter;
import br.com.criptograma.shared.model.Puzzle;

import java.util.ArrayList;
import java.util.List;

public final class GameBuilder {

    private GameBuilder() {
    }

    public static GameBoard from(Puzzle puzzle) {
        if (puzzle == null) {
            throw new IllegalArgumentException("puzzle must not be null");
        }
        List<Letter> letters = new ArrayList<>();
        for (char c : puzzle.cipherText().toCharArray()) {
            if (Character.isLetter(c)) {
                letters.add(new Letter(Character.toUpperCase(c), null, false, false));
            }
        }
        if (letters.isEmpty()) {
            throw new IllegalArgumentException("puzzle cipherText contains no letters");
        }
        return new GameBoard(puzzle, letters, 0);
    }
}
