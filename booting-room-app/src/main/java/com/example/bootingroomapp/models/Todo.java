package com.example.bootingroomapp.models;

import javax.swing.text.StyledEditorKit;

public class Todo {
    private Long id;
    private String text;
    private boolean completed;

    public Todo() {}

    public Todo(String text) {
        this.text = text;
        this.completed = false;
    }

    public Todo(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}

