package com.example.springapi.api.mapper;

import com.example.springapi.api.dto.SignalementDto;
import com.example.springapi.api.model.Signalement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class SignalementMapper {
    public SignalementDto EntityToDto(Signalement signalement){
        SignalementDto signalementDto = new SignalementDto();
        BeanUtils.copyProperties(signalement, signalementDto);
        return signalementDto;
    }

    public Signalement DtoToEntity(SignalementDto signalementDto){
        Signalement signalement = new Signalement();
        BeanUtils.copyProperties(signalementDto, signalement);
        return signalement;
    }
}
