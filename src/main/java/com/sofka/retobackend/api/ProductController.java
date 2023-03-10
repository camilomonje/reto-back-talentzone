package com.sofka.retobackend.api;

import com.sofka.retobackend.domain.dto.ProductDTO;

import com.sofka.retobackend.infraestructure.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Mono<ResponseEntity<?>> saveProduct(@RequestBody Mono<ProductDTO> productDTOMono) {
        return productService.saveProduct(productDTOMono)
                .flatMap(productDTO -> Mono.just(ResponseEntity.ok(productDTO)));
    }

    @GetMapping
    public Mono<ResponseEntity<?>> getProducts() {
        return productService.getProducts().collectList()
                .map(productDTOS ->
                        productDTOS.isEmpty() ?
                            new ResponseEntity<>(Mono.just("No se encontraron productos"), HttpStatus.BAD_REQUEST) :
                            new ResponseEntity<>(Mono.just(productDTOS), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> getProduct(@PathVariable String id) {
        return productService.getProduct(id)
                .map(productDTO ->
                        productDTO.getId() == null ?
                                new ResponseEntity<>(Mono.just("No se encontró el producto"), HttpStatus.BAD_REQUEST) :
                                new ResponseEntity<>(Mono.just(productDTO), HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<ProductDTO>> updateProduct(@RequestBody Mono<ProductDTO> productDTOMono, @PathVariable String id){
        return productService.updateProduct(productDTOMono,id)
                .flatMap(productDTO -> Mono.just(ResponseEntity.ok(productDTO)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id)
                .flatMap(unused -> Mono.just(ResponseEntity.ok("Se eliminó el producto")))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
