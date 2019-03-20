package it.sevenbits.homework.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Task {
    private final UUID id;

    private Status status;

    private String text;

    @JsonCreator
    public Task(@JsonProperty("id") UUID id, @JsonProperty("text") String text) {
        this.id = id;
        this.text = text;
        this.status = new Status();
    }

    public UUID getId() {
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
