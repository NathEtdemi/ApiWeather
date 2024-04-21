package com.example.springapi.api.repository;

import com.example.springapi.api.model.WeatherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherTypeRepository extends JpaRepository<WeatherType, Long> {
}
