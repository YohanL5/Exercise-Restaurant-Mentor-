package com.restaurant.Restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column (nullable = false)
    private String clientDocument;

    @Column(nullable = false)
    private String productUuid;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 511, nullable = false)
    private String extraInformation;

    @Column(nullable = false)
    private Double subTotal;

    @Column(nullable = false)
    private Double tax;

    @Column(nullable = false)
    private Double grandTotal;

    @Column
    private Boolean delivered = false;

    @Column
    private LocalDateTime deliveredDate;

}
