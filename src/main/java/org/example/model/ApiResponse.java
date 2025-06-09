package org.example.model;


import org.springframework.validation.FieldError;

import java.util.List;

public class ApiResponse {
        private String message;
        private Object data;

        // Constructor for successful responses
        public ApiResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        // Constructor for error responses
        public ApiResponse(String message, List<FieldError> errors) {
            this.message = message;
            this.data = errors;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
}
