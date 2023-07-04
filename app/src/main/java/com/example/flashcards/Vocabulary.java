package com.example.flashcards;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {
    private List<Vocable> vocableArrayList;
    private boolean isReversed;

    public Vocabulary() {
        this(false);
    }
    public Vocabulary(boolean isReversed) {
        this.vocableArrayList = new ArrayList<Vocable>();
        this.isReversed = isReversed;
    }
    public void add(Vocable vocable) {
        if(isReversed) {
            vocableArrayList.add(new Vocable(vocable.getForeignWord(), vocable.getNativeWord()));
        } else {
            vocableArrayList.add(vocable);
        }
    }
    public Vocable get(int id) {
        return vocableArrayList.get(id);
    }
    public int size() {
        return vocableArrayList.size();
    }
}
