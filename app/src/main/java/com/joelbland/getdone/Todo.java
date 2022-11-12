package com.joelbland.getdone;

public class Todo {
    private int id;
    private String item;

    public Todo(int newId, String newItem) {
        setId(newId);
        setItem(newItem);
    }

    private void setId(int newId) { id = newId; }

    private void setItem(String newItem) { item = newItem; }

    public int getId() { return id; }

    public String getItem() { return item; }

}