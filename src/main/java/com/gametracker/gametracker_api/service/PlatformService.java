package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.repository.PlatformRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlatformService {

    private final PlatformRepository repo;

    public PlatformService(PlatformRepository repo) {
        this.repo = repo;
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
}
