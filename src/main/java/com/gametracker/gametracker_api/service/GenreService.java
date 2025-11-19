package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Genre;
import com.gametracker.gametracker_api.repository.GenreRepository;
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

    public Genre salvar(Genre g) {
        return repo.save(g);
    }
}
