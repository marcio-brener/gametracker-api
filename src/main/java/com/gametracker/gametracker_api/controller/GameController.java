package com.gametracker.gametracker_api.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Game game) {
        return service.salvar(game);
    }

    // Bind/Unbind Genre to Game
    @PostMapping("/{gameId}/genre/{genreId}")
    public ResponseEntity<?> bindGenre(@PathVariable Long gameId, @PathVariable Long genreId) {
        return service.bindGenre(gameId, genreId);
    }

    @DeleteMapping("/{gameId}/genre")
    public ResponseEntity<?> unbindGenre(@PathVariable Long gameId) {
        return service.unbindGenre(gameId);
    }

    // Bind/Unbind Platform to Game
    @PostMapping("/{gameId}/platform/{platformId}")
    public ResponseEntity<?> bindPlatform(@PathVariable Long gameId, @PathVariable Long platformId) {
        return service.bindPlatform(gameId, platformId);
    }

    @DeleteMapping("/{gameId}/platform/{platformId}")
    public ResponseEntity<?> unbindPlatform(@PathVariable Long gameId, @PathVariable Long platformId) {
        return service.unbindPlatform(gameId, platformId);
    }

    // Get Achievements of a Game
    @GetMapping("/{gameId}/achievements")
    public ResponseEntity<Set<Achievement>> getGameAchievements(@PathVariable Long gameId) {
        return service.getGameAchievements(gameId);
    }

    // Get Platforms of a Game
    @GetMapping("/{gameId}/platforms")
    public ResponseEntity<Set<Platform>> getGamePlatforms(@PathVariable Long gameId) {
        return service.getGamePlatforms(gameId);
    }

    // Update Game (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Game game) {
        return service.update(id, game);
    }

    // Delete Game
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
