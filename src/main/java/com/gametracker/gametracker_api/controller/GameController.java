package com.gametracker.gametracker_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> listar() {
        return service.listar();
    }

    @PostMapping
    public Game criar(@RequestBody Game game) {
        return service.salvar(game);
    }
}
