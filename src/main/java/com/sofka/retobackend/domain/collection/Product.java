package com.sofka.retobackend.domain.collection;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "productos")
public class Product {

    @Id
    private String id;
    private String name;
    private int inInventory;
    private boolean enabled;
    private int min;
    private int max;

}
