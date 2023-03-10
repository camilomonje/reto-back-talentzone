package com.sofka.retobackend.infraestructure.services;

import com.sofka.retobackend.domain.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono);
    Mono<ProductDTO> getProduct(String id);
    Flux<ProductDTO> getProducts();
    Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id);
    Mono<ProductDTO> deleteProduct(String id);
    Mono<Boolean> existProduct(String id);


}
