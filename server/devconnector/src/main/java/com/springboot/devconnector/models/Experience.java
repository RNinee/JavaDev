package com.springboot.devconnector.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    private String title;
    private String company;
    private String location;
    private LocalDate from;
    private LocalDate to;
    private Boolean current;
    private String description;
}
