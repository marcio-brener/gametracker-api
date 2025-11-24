package com.gametracker.gametracker_api.model;

import jakarta.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Genre() {}

    public Long getId() { return id; }
    public String getNome() { return nome; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
}
