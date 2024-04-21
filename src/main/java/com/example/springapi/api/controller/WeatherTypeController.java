package com.example.springapi.api.controller;

import com.example.springapi.api.dto.WeatherTypeDto;
import com.example.springapi.service.WeatherTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/weather-types")
public class WeatherTypeController {

    @Autowired
    private WeatherTypeService weatherTypeService;

    private Logger logger = LoggerFactory.getLogger(WeatherTypeController.class);

    @GetMapping
    public ResponseEntity<List<WeatherTypeDto>> getAllWeatherTypes() {
        try {
            List<WeatherTypeDto> weatherTypes = weatherTypeService.getAllWeatherTypes();
            logger.debug("get all weather types");
            if (weatherTypes.isEmpty()) {
                logger.warn("Aucun type de météo dans la base de données");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(weatherTypes);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherTypeDto> getWeatherTypeById(@PathVariable Long id) {
        try {
            WeatherTypeDto weatherType = weatherTypeService.getWeatherTypeById(id);
            if (weatherType == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(weatherType);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<WeatherTypeDto> createWeatherType(@RequestBody @Valid WeatherTypeDto weatherTypeDto) {
        try {
            WeatherTypeDto createdWeatherType = weatherTypeService.createWeatherType(weatherTypeDto);
            return ResponseEntity
                    .created(new URI("/api/weather-types/" + createdWeatherType.getId()))
                    .body(createdWeatherType);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeatherTypeDto> editWeatherType(@PathVariable Long id, @RequestBody @Valid WeatherTypeDto weatherTypeDto) {
        try {
            WeatherTypeDto editedWeatherType = weatherTypeService.editWeatherType(id, weatherTypeDto);
            if (editedWeatherType == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(editedWeatherType);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeatherType(@PathVariable Long id) {
        try {
            weatherTypeService.deleteWeatherType(id);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().build();
        }
    }
}
