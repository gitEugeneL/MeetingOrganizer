package com.euglihon.meetingorganizer.repository.category;

public final class CategoryQueries {

    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS Categories (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name VARCHAR(255) NOT NULL UNIQUE,
            color VARCHAR(20) NOT NULL CHECK (color IN ('RED', 'YELLOW', 'GREEN', 'BLUE')));
        """;
}
