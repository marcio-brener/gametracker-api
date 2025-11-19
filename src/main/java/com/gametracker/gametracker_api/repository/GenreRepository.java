package com.gametracker.gametracker_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gametracker.gametracker_api.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
