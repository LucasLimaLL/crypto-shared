package br.com.criptograma.shared.model;

import java.util.Collections;
import java.util.List;

public record GameBoard(Puzzle puzzle, List<Letter> letters, int hintsUsed) {

    public GameBoard {
        if (puzzle == null) {
            throw new IllegalArgumentException("puzzle must not be null");
        }
        if (letters == null || letters.isEmpty()) {
            throw new IllegalArgumentException("letters must not be null or empty");
        }
        if (hintsUsed < 0) {
            throw new IllegalArgumentException("hintsUsed must not be negative");
        }
        letters = Collections.unmodifiableList(letters);
    }

    public boolean isSolved() {
        return letters.stream().allMatch(l -> l.confirmed() || l.revealed());
    }

    public long confirmedCount() {
        return letters.stream().filter(l -> l.confirmed() || l.revealed()).count();
    }

    public GameBoard withLetterAt(int index, Letter letter) {
        if (index < 0 || index >= letters.size()) {
            throw new IndexOutOfBoundsException("index out of bounds: " + index);
        }
        var updated = new java.util.ArrayList<>(letters);
        updated.set(index, letter);
        return new GameBoard(puzzle, updated, hintsUsed);
    }

    public GameBoard withHintUsed() {
        return new GameBoard(puzzle, letters, hintsUsed + 1);
    }
}
