package com.example.springapi.api.controller;

import com.example.springapi.api.dto.SignalementDto;
import com.example.springapi.api.model.Signalement;
import com.example.springapi.service.SignalementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/signalement")
public class SignalementController {

    @Autowired
    private SignalementService signalementService;

    private Logger logger = LoggerFactory.getLogger(SignalementController.class);

    @GetMapping
    public ResponseEntity<List<SignalementDto>> getAllSignalements() {
        try {
            List<SignalementDto> signalements = signalementService.getAllSignalement();
            logger.debug("get all signalements");
            if (signalements.isEmpty()) {
                logger.warn("Aucun signalement dans la base de données");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(signalements);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignalementDto> getSignalementById(@PathVariable Long id) {
        try {
            SignalementDto signalement = signalementService.getSignalementById(id);
            if (signalement == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(signalement);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<SignalementDto> createSignalement(@RequestBody @Valid SignalementDto signalementDto) {
        try {
            SignalementDto createdSignalementDto = signalementService.createSignalement(signalementDto);

            return ResponseEntity
                    .created(new URI("/api/signalement/" + createdSignalementDto.getId()))
                    .body(createdSignalementDto);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<SignalementDto> editSignalement(@PathVariable Long id, @RequestBody @Valid SignalementDto signalementDto) {
        try {
            SignalementDto editedSignalement = signalementService.editSignalement(id, signalementDto);
            if (editedSignalement == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(editedSignalement);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignalement(@PathVariable Long id) {
        try {
            signalementService.deleteSignalement(id);
            return ResponseEntity.noContent().build();
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/a-proximite")
    public ResponseEntity<List<SignalementDto>> getReportsNearby(@RequestParam double userLat,
                                                                 @RequestParam double userLon,
                                                                 @RequestParam double radius) {
        try {
            List<SignalementDto> nearbyReports = signalementService.findReportsNearby(userLat, userLon, radius);
            if (nearbyReports.isEmpty()) {
                logger.warn("Aucun signalement à proximité trouvé");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(nearbyReports);
        } catch (Exception exception) {
            logger.error("Problème dans la base de données", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
