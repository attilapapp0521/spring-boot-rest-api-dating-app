
package com.example.datingapp.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {
    private List<CustomFieldError> errors = new ArrayList<>();

    public void addFieldError(String field, String message) {
        CustomFieldError error = new CustomFieldError(field, message);
        errors.add(error);
    }

    public List<CustomFieldError> getFieldErrors() {
        return errors;
    }

    public void setFieldErrors(List<CustomFieldError> customFieldErrors) {
        this.errors = customFieldErrors;
    }

    private static class CustomFieldError {

        private String field;
        private String message;

        CustomFieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
