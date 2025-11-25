package com.gametracker.gametracker_api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.repository.GameRepository;
import com.gametracker.gametracker_api.repository.PlatformRepository;

@Service
public class PlatformService {

    private final PlatformRepository repo;
    private final GameRepository gameRepository;

    public PlatformService(PlatformRepository repo, GameRepository gameRepository) {
        this.repo = repo;
        this.gameRepository = gameRepository;
    }

    public List<Platform> listar() {
        return repo.findAll();
    }

    public ResponseEntity<?> salvar(Platform p) {
        // Validação para o campo nome
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'nome' é obrigatório e não pode estar vazio");
        }
        
        Platform savedPlatform = repo.save(p);
        return ResponseEntity.ok(savedPlatform);
    }

    // Bind Platform to Game
    public ResponseEntity<?> bindToGame(Long platformId, Long gameId) {
        Optional<Platform> platformOpt = repo.findById(platformId);
        Optional<Game> gameOpt = gameRepository.findById(gameId);

        if (platformOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Platform com ID " + platformId + " não encontrada");
        }

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Platform platform = platformOpt.get();
        Game game = gameOpt.get();

        // Add to both sides of the relationship
        game.getPlatforms().add(platform);
        platform.getGames().add(game);
        
        repo.save(platform);
        gameRepository.save(game);

        return ResponseEntity.ok("Platform vinculada ao game com sucesso");
    }

    // Unbind Platform from Game
    public ResponseEntity<?> unbindFromGame(Long platformId, Long gameId) {
        Optional<Platform> platformOpt = repo.findById(platformId);
        Optional<Game> gameOpt = gameRepository.findById(gameId);

        if (platformOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Platform com ID " + platformId + " não encontrada");
        }

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Platform platform = platformOpt.get();
        Game game = gameOpt.get();

        // Remove from both sides of the relationship
        game.getPlatforms().removeIf(p -> p.getId().equals(platformId));
        platform.getGames().removeIf(g -> g.getId().equals(gameId));
        
        repo.save(platform);
        gameRepository.save(game);

        return ResponseEntity.ok("Platform desvinculada do game com sucesso");
    }

    // Get Games of a Platform
    public ResponseEntity<Set<Game>> getPlatformGames(Long platformId) {
        Optional<Platform> platformOpt = repo.findById(platformId);

        if (platformOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Platform platform = platformOpt.get();
        return ResponseEntity.ok(platform.getGames());
    }
}
