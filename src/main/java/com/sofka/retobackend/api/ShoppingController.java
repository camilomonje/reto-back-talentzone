package com.sofka.retobackend.api;

import com.sofka.retobackend.domain.dto.ShoppingDTO;
import com.sofka.retobackend.infraestructure.services.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@CrossOrigin("*")
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    ShoppingService shoppingService;

    @GetMapping
    public Mono<ResponseEntity<?>> getBuys() {
         return shoppingService.getBuys().collectList()
                 .map(shoppingDTOS ->
                                 shoppingDTOS.isEmpty() ?
                                         new ResponseEntity<>(Mono.just("No se encontraron Compras"), HttpStatus.BAD_REQUEST)
                                         : new ResponseEntity<>(Mono.just(shoppingDTOS), HttpStatus.OK));
    }

    @PostMapping
    public Mono<ResponseEntity<?>> saveBuy(@RequestBody Mono<ShoppingDTO> shoppingDTOMono) {
        return shoppingService.saveBuy(shoppingDTOMono)
                .map(shoppingDTO ->
                        shoppingDTO.getProducts().isEmpty() ?
                                new ResponseEntity<>(Mono.just("No se encontraron Productos"), HttpStatus.BAD_REQUEST)
                                : new ResponseEntity<>(Mono.just(shoppingDTO), HttpStatus.OK));
    }
}
