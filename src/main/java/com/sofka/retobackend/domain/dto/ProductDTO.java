package com.sofka.retobackend.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductDTO {

    private String id;
    private String name;
    private int inInventory;
    private boolean enabled;
    private int min;
    private int max;

}
