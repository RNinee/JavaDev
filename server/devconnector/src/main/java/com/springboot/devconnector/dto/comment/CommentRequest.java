package com.springboot.devconnector.dto.comment;

import jakarta.validation.constraints.NotBlank;

public class CommentRequest {

    @NotBlank(message = "Text is required")
    private String text;

    public CommentRequest() {
    }

    public CommentRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
