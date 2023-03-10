package com.sofka.retobackend.domain.collection;

import com.sofka.retobackend.domain.dto.ProductBuyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "compras")
public class Shopping {

    @Id
    private String id;
    private String documentType;
    private String documentNumber;
    private String clientName;
    private LocalDateTime date;
    private List<ProductBuyDTO> products;
}
