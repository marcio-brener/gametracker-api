package com.gametracker.gametracker_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gametracker.gametracker_api.model.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {}
