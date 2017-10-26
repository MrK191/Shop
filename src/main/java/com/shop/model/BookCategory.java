package com.shop.model;

public enum BookCategory {

    SCIENCEFICTION("Science Fiction"),
    DRAMA("Drama"),
    HORROR("Horror"),
    HISTORY("History"),
    ROMANCE("Romance");

    private final String value;

    BookCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BookCategory fromValue(String v) {
        for (BookCategory c : BookCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}

