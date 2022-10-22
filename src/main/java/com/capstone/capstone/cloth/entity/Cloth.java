package com.capstone.capstone.cloth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, updatable = true, unique = false)
    private String category;

    @Column(nullable = false, updatable = true, unique = false)
    private String details;

    @Column(nullable = false, updatable = true, unique = false)
    private String gender;

    @Column(nullable = false, updatable = true, unique = false)
    private String name;

    @Column(nullable = false, updatable = true, unique = false)
    private String brand;

    @Column(nullable = false, updatable = true, unique = false)
    private String price;

    @Column(nullable = false, updatable = true, unique = false)
    private String link;

    @Column(nullable = true, updatable = true, unique = false)
    private String path;

    @Column(nullable = true, updatable = true, unique = false)
    private String path_nukki;
}
