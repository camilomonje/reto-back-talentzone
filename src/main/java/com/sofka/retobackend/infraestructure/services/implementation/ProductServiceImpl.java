package com.sofka.retobackend.infraestructure.services.implementation;

import com.sofka.retobackend.domain.dto.ProductDTO;
import com.sofka.retobackend.infraestructure.repository.ProductRepository;
import com.sofka.retobackend.infraestructure.services.ProductService;
import com.sofka.retobackend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUtils appUtils;

    @Override
    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(appUtils::dtoToEntity)
                .flatMap(productRepository::insert)
                .map(appUtils::entityToDTO);
    }

    @Override
    public Mono<ProductDTO> getProduct(String id) {
        return productRepository.findById(id)
                .map(appUtils::entityToDTO)
                .switchIfEmpty(Mono.just(ProductDTO.builder().build()));
    }

    @Override
    public Flux<ProductDTO> getProducts() {
        return productRepository.findAll()
                .map(appUtils::entityToDTO);

    }

    @Override
    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id) {
        return productRepository.findById(id)
                .flatMap(p -> productDTOMono.map(appUtils::dtoToEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(appUtils::entityToDTO);
    }

    @Override
    public Mono<ProductDTO> deleteProduct(String id) {
        return productRepository.findById(id)
                .map(appUtils::entityToDTO)
                .map(productDTO -> {
                    productRepository.deleteById(productDTO.getId());
                    return productDTO;
                })
                .switchIfEmpty(Mono.empty());









    }

    @Override
    public Mono<Boolean> existProduct(String id) {
        return productRepository.existsById(id);
    }
}
