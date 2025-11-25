package com.gametracker.gametracker_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.model.Genre;
import com.gametracker.gametracker_api.repository.GameRepository;
import com.gametracker.gametracker_api.repository.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository repo;
    private final GameRepository gameRepository;

    public GenreService(GenreRepository repo, GameRepository gameRepository) {
        this.repo = repo;
        this.gameRepository = gameRepository;
    }

    public List<Genre> listar() {
        return repo.findAll();
    }

    public ResponseEntity<?> salvar(Genre g) {
        // Validação para o campo nome
        if (g.getNome() == null || g.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'nome' é obrigatório e não pode estar vazio");
        }
        
        Genre savedGenre = repo.save(g);
        return ResponseEntity.ok(savedGenre);
    }

    // Bind Genre to Game
    public ResponseEntity<?> bindToGame(Long genreId, Long gameId) {
        Optional<Genre> genreOpt = repo.findById(genreId);
        Optional<Game> gameOpt = gameRepository.findById(gameId);

        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Genre com ID " + genreId + " não encontrado");
        }

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Genre genre = genreOpt.get();
        Game game = gameOpt.get();

        game.setGenre(genre);
        gameRepository.save(game);

        return ResponseEntity.ok("Genre vinculado ao game com sucesso");
    }

    // Unbind Genre from Game
    public ResponseEntity<?> unbindFromGame(Long genreId) {
        // First find the game that has this genre
        Optional<Genre> genreOpt = repo.findById(genreId);
        
        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Genre com ID " + genreId + " não encontrado");
        }

        // Find games with this genre (for OneToOne, there should be only one)
        List<Game> gamesWithGenre = gameRepository.findAll().stream()
            .filter(game -> game.getGenre() != null && game.getGenre().getId().equals(genreId))
            .toList();

        if (gamesWithGenre.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nenhum game encontrado com o Genre ID " + genreId);
        }

        // Unbind genre from all games (should be just one)
        for (Game game : gamesWithGenre) {
            game.setGenre(null);
            gameRepository.save(game);
        }

        return ResponseEntity.ok("Genre desvinculado do(s) game(s) com sucesso");
    }

    // Get Game of a Genre
    public ResponseEntity<Game> getGenreGame(Long genreId) {
        Optional<Genre> genreOpt = repo.findById(genreId);

        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Find the game that has this genre
        Optional<Game> gameOpt = gameRepository.findAll().stream()
            .filter(game -> game.getGenre() != null && game.getGenre().getId().equals(genreId))
            .findFirst();

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(gameOpt.get());
    }

    // Full update for Genre (PUT)
    public ResponseEntity<?> update(Long id, Genre genreUpdate) {
        Optional<Genre> genreOpt = repo.findById(id);

        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Genre com ID " + id + " não encontrado");
        }

        // Validation for nome field
        if (genreUpdate.getNome() == null || genreUpdate.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'nome' é obrigatório e não pode estar vazio");
        }

        Genre existingGenre = genreOpt.get();
        existingGenre.setNome(genreUpdate.getNome());

        Genre updatedGenre = repo.save(existingGenre);
        return ResponseEntity.ok(updatedGenre);
    }

    // Delete Genre
    public ResponseEntity<?> delete(Long id) {
        Optional<Genre> genreOpt = repo.findById(id);

        if (genreOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Genre com ID " + id + " não encontrado");
        }

        // Check if genre is being used by any game
        List<Game> gamesWithGenre = gameRepository.findAll().stream()
            .filter(game -> game.getGenre() != null && game.getGenre().getId().equals(id))
            .toList();

        if (!gamesWithGenre.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Não é possível deletar o genre pois ele está sendo usado por " + gamesWithGenre.size() + " game(s)");
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Genre deletado com sucesso");
    }
}
