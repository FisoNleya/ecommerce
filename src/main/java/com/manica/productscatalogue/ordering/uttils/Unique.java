package com.manica.productscatalogue.ordering.uttils;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "unique_util")
@NoArgsConstructor
@AllArgsConstructor
public class Unique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uniqueId;



}
