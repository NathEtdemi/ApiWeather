package com.example.springapi.service;

import com.example.springapi.api.dto.SignalementDto;
import com.example.springapi.api.mapper.SignalementMapper;
import com.example.springapi.api.model.Signalement;
import com.example.springapi.api.repository.SignalementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignalementService {

    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private SignalementMapper signalementMapper;

    public List<SignalementDto> getAllSignalement() {
        List<Signalement> signalements = signalementRepository.findAll();
        return signalements.stream()
                .map(signalementMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    public SignalementDto getSignalementById(Long id) {
        return signalementMapper.EntityToDto(signalementRepository.findById(id).orElse(null));
    }

    public SignalementDto createSignalement(SignalementDto signalementDto) {
        Signalement signalement = signalementMapper.DtoToEntity(signalementDto);
        return signalementMapper.EntityToDto(signalementRepository.save(signalement));
    }

    public SignalementDto editSignalement(Long id, SignalementDto signalementDto) {
        Signalement existingSignalement = signalementRepository.findById(id).orElse(null);
        if (existingSignalement != null) {
            Signalement updatedSignalement = signalementMapper.DtoToEntity(signalementDto);
            updatedSignalement.setId(id);
            return signalementMapper.EntityToDto(signalementRepository.save(updatedSignalement));
        }
        return null;
    }

    public void deleteSignalement(Long id) {
        signalementRepository.deleteById(id);
    }
}
