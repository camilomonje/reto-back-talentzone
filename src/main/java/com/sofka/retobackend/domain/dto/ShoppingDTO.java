package com.sofka.retobackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ShoppingDTO {

    private String id;
    private String documentType;
    private String documentNumber;
    private String clientName;
    private LocalDateTime date;
    private List<ProductBuyDTO> products;

}
