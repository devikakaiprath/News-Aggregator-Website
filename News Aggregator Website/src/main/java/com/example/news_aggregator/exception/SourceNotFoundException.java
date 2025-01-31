package com.example.news_aggregator.exception;

public class SourceNotFoundException extends RuntimeException {
    public SourceNotFoundException(String message) {
        super(message);
    }
}
