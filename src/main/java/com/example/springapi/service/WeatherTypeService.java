package com.example.springapi.service;

import com.example.springapi.api.dto.WeatherTypeDto;
import com.example.springapi.api.mapper.WeatherTypeMapper;
import com.example.springapi.api.model.WeatherType;
import com.example.springapi.api.repository.WeatherTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherTypeService {

    @Autowired
    private WeatherTypeRepository weatherTypeRepository;

    @Autowired
    private WeatherTypeMapper weatherTypeMapper;

    public List<WeatherTypeDto> getAllWeatherTypes() {
        List<WeatherType> weatherTypes = weatherTypeRepository.findAll();
        return weatherTypes.stream()
                .map(weatherTypeMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    public WeatherTypeDto getWeatherTypeById(Long id) {
        return weatherTypeMapper.EntityToDto(weatherTypeRepository.findById(id).orElse(null));
    }

    public WeatherTypeDto createWeatherType(WeatherTypeDto weatherTypeDto) {
        WeatherType weatherType = weatherTypeMapper.DtoToEntity(weatherTypeDto);
        return weatherTypeMapper.EntityToDto(weatherTypeRepository.save(weatherType));
    }

    public WeatherTypeDto editWeatherType(Long id, WeatherTypeDto weatherTypeDto) {
        WeatherType existingWeatherType = weatherTypeRepository.findById(id).orElse(null);
        if (existingWeatherType != null) {
            WeatherType updatedWeatherType = weatherTypeMapper.DtoToEntity(weatherTypeDto);
            updatedWeatherType.setId(id);
            return weatherTypeMapper.EntityToDto(weatherTypeRepository.save(updatedWeatherType));
        }
        return null;
    }

    public void deleteWeatherType(Long id) {
        weatherTypeRepository.deleteById(id);
    }

    public WeatherType findByName(String name) {
        return weatherTypeRepository.findByName(name);
    }
}
