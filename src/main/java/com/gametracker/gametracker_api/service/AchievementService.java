package com.gametracker.gametracker_api.service;

import com.gametracker.gametracker_api.model.Achievement;
import com.gametracker.gametracker_api.repository.AchievementRepository;
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

    public Achievement salvar(Achievement a) {
        return repo.save(a);
    }
}
