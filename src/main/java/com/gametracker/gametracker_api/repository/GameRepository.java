package com.gametracker.gametracker_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gametracker.gametracker_api.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {}
