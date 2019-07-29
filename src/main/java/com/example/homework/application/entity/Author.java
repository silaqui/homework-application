package com.example.homework.application.entity;

public class Author {

    private String author;
    private Double averageRating;

    public Author() {
    }

    public Author(String author, Double averageRating) {
        this.author = author;
        this.averageRating = averageRating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
