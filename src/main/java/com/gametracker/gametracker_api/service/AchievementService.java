package com.gametracker.gametracker_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.repository.AchievementRepository;
import com.gametracker.gametracker_api.repository.GameRepository;

@Service
public class AchievementService {

    private final AchievementRepository repo;
    private final GameRepository gameRepository;

    public AchievementService(AchievementRepository repo, GameRepository gameRepository) {
        this.repo = repo;
        this.gameRepository = gameRepository;
    }

    public List<Achievement> listar() {
        return repo.findAll();
    }

    public ResponseEntity<?> salvar(Achievement a) {
        // Validação para o campo nome
        if (a.getNome() == null || a.getNome().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'nome' é obrigatório e não pode estar vazio");
        }
        
        // Validação para o campo descricao
        if (a.getDescricao() == null || a.getDescricao().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O campo 'descricao' é obrigatório e não pode estar vazio");
        }
        
        Achievement savedAchievement = repo.save(a);
        return ResponseEntity.ok(savedAchievement);
    }

    // Bind Achievement to Game
    public ResponseEntity<?> bindToGame(Long achievementId, Long gameId) {
        Optional<Achievement> achievementOpt = repo.findById(achievementId);
        Optional<Game> gameOpt = gameRepository.findById(gameId);

        if (achievementOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Achievement com ID " + achievementId + " não encontrado");
        }

        if (gameOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Game com ID " + gameId + " não encontrado");
        }

        Achievement achievement = achievementOpt.get();
        Game game = gameOpt.get();

        achievement.setGame(game);
        repo.save(achievement);

        return ResponseEntity.ok("Achievement vinculado ao game com sucesso");
    }

    // Unbind Achievement from Game
    public ResponseEntity<?> unbindFromGame(Long achievementId) {
        Optional<Achievement> achievementOpt = repo.findById(achievementId);

        if (achievementOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Achievement com ID " + achievementId + " não encontrado");
        }

        Achievement achievement = achievementOpt.get();
        achievement.setGame(null);
        repo.save(achievement);

        return ResponseEntity.ok("Achievement desvinculado do game com sucesso");
    }

    // Partial update for Achievement (PATCH)
    public ResponseEntity<?> update(Long id, Achievement achievementUpdate) {
        Optional<Achievement> achievementOpt = repo.findById(id);

        if (achievementOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Achievement com ID " + id + " não encontrado");
        }

        Achievement existingAchievement = achievementOpt.get();

        // Update only non-null fields
        if (achievementUpdate.getNome() != null && !achievementUpdate.getNome().trim().isEmpty()) {
            existingAchievement.setNome(achievementUpdate.getNome());
        }

        if (achievementUpdate.getDescricao() != null && !achievementUpdate.getDescricao().trim().isEmpty()) {
            existingAchievement.setDescricao(achievementUpdate.getDescricao());
        }

        if (achievementUpdate.getGame() != null) {
            Optional<Game> gameOpt = gameRepository.findById(achievementUpdate.getGame().getId());
            if (gameOpt.isPresent()) {
                existingAchievement.setGame(gameOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Game com ID " + achievementUpdate.getGame().getId() + " não encontrado");
            }
        }

        Achievement updatedAchievement = repo.save(existingAchievement);
        return ResponseEntity.ok(updatedAchievement);
    }

    // Delete Achievement
    public ResponseEntity<?> delete(Long id) {
        Optional<Achievement> achievementOpt = repo.findById(id);

        if (achievementOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Achievement com ID " + id + " não encontrado");
        }

        repo.deleteById(id);
        return ResponseEntity.ok("Achievement deletado com sucesso");
    }
}
