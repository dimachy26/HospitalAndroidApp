package com.example.myapplication.activities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse {
    @SerializedName("duplicateFields")
    private List<String> duplicateFields;

    public List<String> getDuplicateFields() {
        return duplicateFields;
    }

    public void setDuplicateFields(List<String> duplicateFields) {
        this.duplicateFields = duplicateFields;
    }
}
