package com.example.flashcards;

public class Vocable {
    private String nativeWord;
    private String foreignWord;

    public Vocable(String nativeWord, String foreignWord) {
        this.nativeWord = nativeWord;
        this.foreignWord = foreignWord;
    }

    public String getNativeWord() {
        return nativeWord;
    }

    public void setNativeWord(String nativeWord) {
        this.nativeWord = nativeWord;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }
}
