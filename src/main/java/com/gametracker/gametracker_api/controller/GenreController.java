package com.gametracker.gametracker_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gametracker.gametracker_api.model.Game;
import com.gametracker.gametracker_api.model.Genre;
import com.gametracker.gametracker_api.service.GenreService;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public List<Genre> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Genre g) {
        return service.salvar(g);
    }

    // Bind Genre to Game
    @PostMapping("/{genreId}/game/{gameId}")
    public ResponseEntity<?> bindToGame(@PathVariable Long genreId, @PathVariable Long gameId) {
        return service.bindToGame(genreId, gameId);
    }

    // Unbind Genre from Game
    @DeleteMapping("/{genreId}/game")
    public ResponseEntity<?> unbindFromGame(@PathVariable Long genreId) {
        return service.unbindFromGame(genreId);
    }

    // Get Game of a Genre
    @GetMapping("/{genreId}/game")
    public ResponseEntity<Game> getGenreGame(@PathVariable Long genreId) {
        return service.getGenreGame(genreId);
    }

    // Partial update Genre (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Genre genre) {
        return service.update(id, genre);
    }

    // Delete Genre
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
