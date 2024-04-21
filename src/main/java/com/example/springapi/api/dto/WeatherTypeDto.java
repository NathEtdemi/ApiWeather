package com.example.springapi.api.dto;

public class WeatherTypeDto {
    private Long id;
    private String name;

    public WeatherTypeDto() {
    }

    public WeatherTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
