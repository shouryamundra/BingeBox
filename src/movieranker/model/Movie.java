package movieranker.model;

import java.util.Objects;

public record Movie(String title, String[] genre, int yearOfRelease, int duration) {
    public Movie {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(genre, "Genre cannot be null");

        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        if (yearOfRelease < 1900) { // first known film date
            throw new IllegalArgumentException("Year of release is invalid");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
    }
}

