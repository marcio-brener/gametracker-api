package com.gametracker.gametracker_api.service;

import java.util.List;

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

    public Game salvar(Game game) {
        return repo.save(game);
    }
}
