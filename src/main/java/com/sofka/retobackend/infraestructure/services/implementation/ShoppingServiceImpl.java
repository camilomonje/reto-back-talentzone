package com.sofka.retobackend.infraestructure.services.implementation;

import com.sofka.retobackend.domain.dto.ProductBuyDTO;
import com.sofka.retobackend.domain.dto.ProductDTO;
import com.sofka.retobackend.domain.dto.ShoppingDTO;
import com.sofka.retobackend.infraestructure.repository.ShoppingRepository;
import com.sofka.retobackend.infraestructure.services.ProductService;
import com.sofka.retobackend.infraestructure.services.ShoppingService;
import com.sofka.retobackend.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AppUtils appUtils;

    @Override
    public Mono<ShoppingDTO> saveBuy(Mono<ShoppingDTO> shoppingDTO) {
        return shoppingDTO
                .flatMap(shoppingDTO1 -> validatedAvailabilityProduct(shoppingDTO1)
                        .map(productBuyDTOS -> buildShoppingDTO(shoppingDTO1, productBuyDTOS)))
                .flatMap(shoppingDTO1 -> validateInInventoryProduct(shoppingDTO1)
                        .map(productBuyDTOS -> buildShoppingDTO(shoppingDTO1, productBuyDTOS)))
                .map(appUtils::dtoToEntityShopping)
                .flatMap(shopping -> shopping.getProducts().isEmpty() ? Mono.just(shopping) : shoppingRepository.insert(shopping))
                .map(appUtils::entityToDTOShopping);
    }

    private Mono<List<ProductBuyDTO>> validateInInventoryProduct(ShoppingDTO shoppingDTO1) {
        return Flux.fromIterable(shoppingDTO1.getProducts())
                .flatMap(this::validarInInventory)
                .collectList();
    }

    private Mono<ProductBuyDTO> validarInInventory(ProductBuyDTO productBuyDTO) {
        return productService.getProduct(productBuyDTO.getIdProduct())
                .flatMap(productDTO ->
                        ((productDTO.getInInventory() > productBuyDTO.getQuantity()) && productDTO.isEnabled()
                                && (productDTO.getMin() <= productBuyDTO.getQuantity())
                                && (productDTO.getMax() >= productBuyDTO.getQuantity()) ?
                                updateQuantity(productDTO
                                        .toBuilder()
                                        .inInventory(productDTO.getInInventory() - productBuyDTO.getQuantity())
                                        .build(), productBuyDTO)
                                : Mono.empty())
                );

    }

    private Mono<ProductBuyDTO> updateQuantity(ProductDTO productDTO, ProductBuyDTO productBuyDTO) {
        return productService.updateProduct(Mono.just(productDTO), productBuyDTO.getIdProduct())
                .flatMap(productDTO1 -> Mono.just(buildProducBuyDTO(productBuyDTO, productDTO)));
    }

    private static ProductBuyDTO buildProducBuyDTO(ProductBuyDTO productBuyDTO, ProductDTO productDTO) {
        return productBuyDTO
                .toBuilder()
                .idProduct(productBuyDTO.getIdProduct())
                .name(productDTO.getName())
                .quantity(productBuyDTO.getQuantity())
                .build();
    }

    private static ShoppingDTO buildShoppingDTO(ShoppingDTO shoppingDTO1, List<ProductBuyDTO> productBuyDTOS) {
        return shoppingDTO1
                .toBuilder()
                .date(LocalDateTime.now())
                .products(productBuyDTOS)
                .build();
    }

    private Mono<List<ProductBuyDTO>> validatedAvailabilityProduct(ShoppingDTO shoppingDTO1) {
        return Flux.fromIterable(shoppingDTO1.getProducts())
                .flatMap(this::validar)
                .collectList();
    }

    private Mono<ProductBuyDTO> validar(ProductBuyDTO productBuyDTO) {
        return productService.existProduct(productBuyDTO.getIdProduct())
                .flatMap(aBoolean -> metodo(aBoolean, productBuyDTO));
    }

    private Mono<ProductBuyDTO> metodo(Boolean aBoolean, ProductBuyDTO productBuyDTO) {
        return aBoolean ? Mono.just(productBuyDTO) : Mono.empty();
    }

    @Override
    public Flux<ShoppingDTO> getBuys() {
        return shoppingRepository.findAll()
                .map(appUtils::entityToDTOShopping);
    }
}
