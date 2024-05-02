package com.example.springapi.service;

import com.example.springapi.api.dto.SignalementDto;
import com.example.springapi.api.mapper.SignalementMapper;
import com.example.springapi.api.model.Signalement;
import com.example.springapi.api.model.WeatherType;
import com.example.springapi.api.repository.SignalementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignalementService {

    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private SignalementMapper signalementMapper;

    @Autowired
    private WeatherTypeService weatherTypeService;

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
        signalement.setCreatedAt(new Date()); // Initialisation de la date actuelle

        // Recherche des signalements existants dans un rayon de 500 mètres
        List<Signalement> signalementsDansRayon = signalementRepository.findSignalementsNearby(signalement.getLatitude(), signalement.getLongitude(), 0.5);

        // Suppression des signalements existants dans le rayon
        signalementRepository.deleteAll(signalementsDansRayon);

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

    public List<SignalementDto> findReportsNearby(double userLat, double userLon, double radius) {
        return signalementRepository.findAll().stream()
                .filter(signalement -> calculateDistance(userLat, userLon, signalement.getLatitude(), signalement.getLongitude()) <= radius)
                .sorted(Comparator.comparingDouble((Signalement signalement) -> calculateDistance(userLat, userLon, signalement.getLatitude(), signalement.getLongitude()))
                        .thenComparing(Signalement::getCreatedAt))
                .limit(10)
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    public SignalementDto convertEntityToDto(Signalement signalement) {
        SignalementDto signalementDto = new SignalementDto();
        signalementDto.setId(signalement.getId());
        signalementDto.setLatitude(signalement.getLatitude());
        signalementDto.setLongitude(signalement.getLongitude());
        signalementDto.setTemperature(signalement.getTemperature());
        WeatherType weatherType = weatherTypeService.findByName(signalement.getWeatherType().getName());
        signalementDto.setWeatherType(weatherType);
        signalementDto.setCreatedAt(signalement.getCreatedAt());

        return signalementDto;
    }
}
