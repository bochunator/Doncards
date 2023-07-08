package com.example.flashcards;

public class Vocable {
    private int id;
    private String nativeWord;
    private String foreignWord;

    public Vocable(int id, String nativeWord, String foreignWord) throws IllegalArgumentException {
        if (nativeWord.isEmpty()) {
            throw new IllegalArgumentException("Native is empty");
        }
        if (foreignWord.isEmpty()) {
            throw new IllegalArgumentException("Foreign is empty");
        }
        this.id = id;
        this.nativeWord = nativeWord;
        this.foreignWord = foreignWord;
    }

    public String getNativeWord() {
        return nativeWord;
    }


    public String getForeignWord() {
        return foreignWord;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nativeWord + " - " + foreignWord;
    }
}
