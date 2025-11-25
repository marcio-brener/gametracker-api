package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Genre;
import com.gametracker.gametracker_api.repository.GenreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepository repo;

    public GenreService(GenreRepository repo) {
        this.repo = repo;
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
}
