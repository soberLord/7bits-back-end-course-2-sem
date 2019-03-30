package it.sevenbits.database.core.model;

import java.util.UUID;

public class Task {
    private final long id;

    private Status status;

    private String text;

    public Task(long id, String text, String status) {
        this.id = id;
        this.text = text;
        this.status = new Status(status);
    }

    public long getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void patchText(String text) {
        this.text = text;
    }
    public String getStatus() {
        return status.getStatus();
    }
    public void patchStatus(String status) {
        this.status.setStatus(status);
    }
}
