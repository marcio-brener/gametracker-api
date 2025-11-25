package com.gametracker.gametracker_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository repo;

    public GameService(GameRepository repo) {
        this.repo = repo;
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
}
