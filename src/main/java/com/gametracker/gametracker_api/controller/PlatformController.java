package com.gametracker.gametracker_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.service.PlatformService;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    private final PlatformService service;

    public PlatformController(PlatformService service) {
        this.service = service;
    }

    @GetMapping
    public List<Platform> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Platform p) {
        return service.salvar(p);
    }
}
