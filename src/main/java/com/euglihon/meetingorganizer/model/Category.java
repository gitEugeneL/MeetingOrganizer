package com.euglihon.meetingorganizer.model;

import com.euglihon.meetingorganizer.model.enums.Color;

public class Category {
    private int id;
    private String name;
    private Color color;

    public Category() {}

    public Category(int id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return this.name + " | " + this.color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public String getColorCode() {
        return switch (color) {
            case RED -> "#FF0000";
            case YELLOW -> "#FFFF00";
            case GREEN -> "#00FF00";
            case BLUE -> "#0000FF";
        };
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
