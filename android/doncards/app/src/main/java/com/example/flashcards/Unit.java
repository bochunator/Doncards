package com.example.flashcards;

public class Unit {
    private int id;
    private String name;
    public Unit(int id, String name) throws IllegalArgumentException {
        if(name.equals("")) {
            throw new IllegalArgumentException("Name is empty");
        }
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return name;
    }
}
