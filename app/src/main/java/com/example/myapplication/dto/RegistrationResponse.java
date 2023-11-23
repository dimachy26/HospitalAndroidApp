package com.example.myapplication.dto;

import java.util.List;

public class RegistrationResponse {
    private boolean success;
    private List<String> duplicateFields;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getDuplicateFields() {
        return duplicateFields;
    }

    public void setDuplicateFields(List<String> duplicateFields) {
        this.duplicateFields = duplicateFields;
    }
}
