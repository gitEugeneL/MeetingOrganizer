package com.euglihon.meetingorganizer.model;

import java.time.LocalDate;
import java.util.List;

public class Event {
    private int id;
    private String title;
    private LocalDate date;
    private List<Contact> participants;
    private int categoryId;

    public Event() {}

    public Event(int id, String title, LocalDate date, List<Contact> participants, int categoryId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.participants = participants;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Contact> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Contact> participants) {
        this.participants = participants;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory(List<Category> categories) {
        return categories.stream()
                .filter(category -> category.getId() == this.categoryId)
                .findFirst()
                .orElse(null);
    }
}
