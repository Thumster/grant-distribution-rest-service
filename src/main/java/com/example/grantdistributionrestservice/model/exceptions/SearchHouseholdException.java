package com.example.grantdistributionrestservice.model.exceptions;

import java.util.Map;

public class SearchHouseholdException extends Exception {

    private final Map<String, String> errors;

    public SearchHouseholdException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
