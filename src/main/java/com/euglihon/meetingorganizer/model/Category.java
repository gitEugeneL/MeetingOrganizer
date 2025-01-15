package com.euglihon.meetingorganizer.model;

import com.euglihon.meetingorganizer.model.enums.Color;

/**
 * Represents a category that can be assigned to events.
 */
public class Category {
    private int id;
    private String name;
    private Color color;

    /**
     * Default constructor.
     * Initializes a new Category object without setting any properties.
     */
    public Category() {}

    /**
     * Constructor with parameters to initialize a Category object with an id, name, and color.
     *
     * @param id    the unique identifier for the category
     * @param name  the name of the category
     * @param color the color associated with the category
     */
    public Category(int id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Returns a string representation of the Category object.
     * The format is: "name | color"
     *
     * @return a string representing the category's name and color name
     */
    @Override
    public String toString() {
        return this.name + " | " + this.color;
    }

    /**
     * Gets the unique identifier of the category.
     *
     * @return the id of the category
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the category.
     *
     * @param id the unique identifier to set for the category
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the category.
     *
     * @return the name of the category
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name of the category.
     *
     * @param name the name to set for the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the color associated with the category.
     *
     * @return the color of the category
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the hex color code.
     *
     * @return a hex color code.
     */
    public String getColorCode() {
        return switch (color) {
            case RED -> "#FF0000";
            case YELLOW -> "#FFFF00";
            case GREEN -> "#00FF00";
            case BLUE -> "#0000FF";
        };
    }

    /**
     * Sets the color for the category.
     *
     * @param color the color to assign to the category
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
