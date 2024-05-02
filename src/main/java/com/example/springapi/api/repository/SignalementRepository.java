package com.example.springapi.api.repository;

import com.example.springapi.api.dto.SignalementDto;
import com.example.springapi.api.model.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long> {
    // Autres méthodes de repository

    @Query("SELECT s FROM Signalement s WHERE FUNCTION('acos', FUNCTION('sin', FUNCTION('radians', :latitude)) * FUNCTION('sin', FUNCTION('radians', s.latitude)) + " +
            "FUNCTION('cos', FUNCTION('radians', :latitude)) * FUNCTION('cos', FUNCTION('radians', s.latitude)) * " +
            "FUNCTION('cos', FUNCTION('radians', :longitude) - FUNCTION('radians', s.longitude))) * 6371 <= :radius")
    List<Signalement> findSignalementsNearby(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);
}