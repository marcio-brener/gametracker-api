package com.gametracker.gametracker_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gametracker.gametracker_api.model.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {}
