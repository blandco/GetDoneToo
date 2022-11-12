package com.joelbland.getdone;

public class Todo {
    private int id;
    private String item;
    private String deadline;

    public Todo(int newId, String newItem, String newDeadline) {
        setId(newId);
        setItem(newItem);
        setDeadline(newDeadline);
    }

    private void setId(int newId) { id = newId; }

    private void setItem(String newItem) { item = newItem; }

    private void setDeadline(String newDeadline) { deadline = newDeadline; }

    public int getId() { return id; }

    public String getItem() { return item; }

    public String getDeadline() { return deadline; }

}