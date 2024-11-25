package com.resolveai.stations.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postalCode;
    private String street;
    private String neighborhood;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    private Integer number;

}
