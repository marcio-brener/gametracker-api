package com.gametracker.gametracker_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.service.AchievementService;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService service;

    public AchievementController(AchievementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Achievement> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Achievement a) {
        return service.salvar(a);
    }

    // Bind Achievement to Game
    @PostMapping("/{achievementId}/game/{gameId}")
    public ResponseEntity<?> bindToGame(@PathVariable Long achievementId, @PathVariable Long gameId) {
        return service.bindToGame(achievementId, gameId);
    }

    // Unbind Achievement from Game
    @DeleteMapping("/{achievementId}/game")
    public ResponseEntity<?> unbindFromGame(@PathVariable Long achievementId) {
        return service.unbindFromGame(achievementId);
    }

    // Partial update Achievement (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartial(@PathVariable Long id, @RequestBody Achievement achievement) {
        return service.updatePartial(id, achievement);
    }

    // Delete Achievement
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
