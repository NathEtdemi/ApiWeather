package com.example.springapi.api.mapper;

import com.example.springapi.api.dto.WeatherTypeDto;
import com.example.springapi.api.model.WeatherType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class WeatherTypeMapper {

    public WeatherTypeDto EntityToDto(WeatherType weatherType){
        WeatherTypeDto weatherTypeDto = new WeatherTypeDto();
        BeanUtils.copyProperties(weatherType, weatherTypeDto);
        return weatherTypeDto;
    }

    public WeatherType DtoToEntity(WeatherTypeDto weatherTypeDto){
        WeatherType weatherType = new WeatherType();
        BeanUtils.copyProperties(weatherTypeDto, weatherType);
        return weatherType;
    }
}
