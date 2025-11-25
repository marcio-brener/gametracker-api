package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.repository.AchievementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AchievementService {

    private final AchievementRepository repo;

    public AchievementService(AchievementRepository repo) {
        this.repo = repo;
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
}
