package com.springboot.devconnector.dto.post;

import jakarta.validation.constraints.NotBlank;

public class PostRequest {

    @NotBlank(message = "Text is required")
    private String text;

    public PostRequest() {
    }

    public PostRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
