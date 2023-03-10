package com.sofka.retobackend.infraestructure.services;

import com.sofka.retobackend.domain.dto.ShoppingDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShoppingService {

    Mono<ShoppingDTO> saveBuy(Mono<ShoppingDTO> shoppingDTO);
    Flux<ShoppingDTO> getBuys();
}
