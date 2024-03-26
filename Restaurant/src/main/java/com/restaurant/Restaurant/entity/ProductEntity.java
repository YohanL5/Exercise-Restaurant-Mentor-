package com.restaurant.Restaurant.entity;

import lombok.*;


import jakarta.persistence.*;

import java.util.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    public enum Category{
        HAMBURGERS_AND_HOTDOGS, CHICKEN, FISH, MEATS, DESSERTS, VEGAN_FOOD, KIDS_MEALS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "fantasy_name", nullable = false)
    private String fantasyName;

    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "available", nullable = false)
    private Boolean available;
}
