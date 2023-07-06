package com.example.flashcards;

public class Vocable {
    private String nativeWord;
    private String foreignWord;
    private int id;

    public Vocable(int id, String nativeWord, String foreignWord) throws IllegalArgumentException {
        if (nativeWord.isEmpty()) {
            throw new IllegalArgumentException("Native word is empty");
        }
        if (foreignWord.isEmpty()) {
            throw new IllegalArgumentException("Foreign word is empty");
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

    public void setId(int id) {
        this.id = id;
    }
}
