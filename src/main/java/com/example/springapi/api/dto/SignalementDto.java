package com.example.springapi.api.dto;

import com.example.springapi.api.model.WeatherType;

import java.util.Date;

public class SignalementDto {
    private Long id;
    private WeatherType weatherType;
    private double latitude;
    private double longitude;
    private double temperature;
    private Date createdAt;

    public SignalementDto() {
    }

    public SignalementDto(Long id, WeatherType weatherType, double latitude, double longitude, double temperature, Date createdAt) {
        this.id = id;
        this.weatherType = weatherType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
        this.createdAt = createdAt;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
