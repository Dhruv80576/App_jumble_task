package com.example.sample2;

public class ListModel {
    private String word_model;

    public String getWord_model() {
        return word_model;
    }

    public String getLetter_model() {
        return letter_model;
    }

    public ListModel(String word_model, String letter_model) {
        this.word_model = word_model;
        this.letter_model = letter_model;
    }

    private String letter_model;
}
