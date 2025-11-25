package com.gametracker.gametracker_api.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.model.Genre;
import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.repository.GameRepository;
import com.gametracker.gametracker_api.repository.GenreRepository;
import com.gametracker.gametracker_api.repository.PlatformRepository;

@Service
public class GameService {

    private final GameRepository repo;
    private final GenreRepository genreRepository;
    private final PlatformRepository platformRepository;

    public GameService(GameRepository repo, GenreRepository genreRepository, PlatformRepository platformRepository) {
        this.repo = repo;
        this.genreRepository = genreRepository;
        this.platformRepository = platformRepository;
    }

    public List<Game> listar() {
        return repo.findAll();
    }

    public ResponseEntity<?> salvar(Game game) {
        // Validação para o campo titulo
        if (game.getTitulo() == null || game.getTitulo().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'titulo' é obrigatório e não pode estar vazio");
        }
        
        // Validação para o campo descricao
        if (game.getDescricao() == null || game.getDescricao().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'descricao' é obrigatório e não pode estar vazio");
        }
        
        Game savedGame = repo.save(game);
        return ResponseEntity.ok(savedGame);
    }

    // Genre binding methods
    public ResponseEntity<?> bindGenre(Long gameId, Long genreId) {
        Optional<Game> gameOpt = repo.findById(gameId);
        Optional<Genre> genreOpt = genreRepository.findById(genreId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Genre com ID " + genreId + " não encontrado");
        }

        Game game = gameOpt.get();
        Genre genre = genreOpt.get();

        game.setGenre(genre);
        repo.save(game);

        return ResponseEntity.ok("Genre vinculado ao game com sucesso");
    }

    public ResponseEntity<?> unbindGenre(Long gameId) {
        Optional<Game> gameOpt = repo.findById(gameId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Game game = gameOpt.get();
        game.setGenre(null);
        repo.save(game);

        return ResponseEntity.ok("Genre desvinculado do game com sucesso");
    }

    // Platform binding methods
    public ResponseEntity<?> bindPlatform(Long gameId, Long platformId) {
        Optional<Game> gameOpt = repo.findById(gameId);
        Optional<Platform> platformOpt = platformRepository.findById(platformId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        if (platformOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Platform com ID " + platformId + " não encontrada");
        }

        Game game = gameOpt.get();
        Platform platform = platformOpt.get();

        game.getPlatforms().add(platform);
        repo.save(game);

        return ResponseEntity.ok("Platform vinculada ao game com sucesso");
    }

    public ResponseEntity<?> unbindPlatform(Long gameId, Long platformId) {
        Optional<Game> gameOpt = repo.findById(gameId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Game game = gameOpt.get();
        game.getPlatforms().removeIf(platform -> platform.getId().equals(platformId));
        repo.save(game);

        return ResponseEntity.ok("Platform desvinculada do game com sucesso");
    }

    // Get related entities methods
    public ResponseEntity<Set<Achievement>> getGameAchievements(Long gameId) {
        Optional<Game> gameOpt = repo.findById(gameId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Game game = gameOpt.get();
        return ResponseEntity.ok(game.getAchievements());
    }

    public ResponseEntity<Set<Platform>> getGamePlatforms(Long gameId) {
        Optional<Game> gameOpt = repo.findById(gameId);

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Game game = gameOpt.get();
        return ResponseEntity.ok(game.getPlatforms());
    }
}
