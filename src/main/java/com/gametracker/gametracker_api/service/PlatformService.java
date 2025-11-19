package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Platform;
import com.gametracker.gametracker_api.repository.PlatformRepository;
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

    public Platform salvar(Platform p) {
        return repo.save(p);
    }
}
