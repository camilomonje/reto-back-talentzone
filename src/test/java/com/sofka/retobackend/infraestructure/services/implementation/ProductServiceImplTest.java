package com.sofka.retobackend.infraestructure.services.implementation;

import com.sofka.retobackend.domain.collection.Product;
import com.sofka.retobackend.domain.dto.ProductDTO;
import com.sofka.retobackend.infraestructure.repository.ProductRepository;
import com.sofka.retobackend.utils.AppUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.Verifier;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl service;

    @Mock
    private ProductRepository repository;

    @Mock
    private AppUtils appUtils;

    private ProductDTO productDTO;
    private Product product;

    @Before
    public void setUp() {

        product = new Product();
        product.setId("12345");
        product.setName("Manzana");
        product.setEnabled(true);
        product.setMin(1);
        product.setMax(10);
        product.setInInventory(200);


        productDTO = new ProductDTO();
        productDTO.setId("12345");
        productDTO.setName("Manzana");
        productDTO.setEnabled(true);
        productDTO.setMin(1);
        productDTO.setMax(10);
        productDTO.setInInventory(200);

    }

    @Test
    public void getProductsTest() {

        when(repository.findAll()).thenReturn(Flux.just(product));
        when(appUtils.entityToDTO(product)).thenReturn(productDTO);
        when(appUtils.dtoToEntity(productDTO)).thenReturn(product);

        final Flux<ProductDTO> result = service.getProducts();

        StepVerifier.create(result).expectNext(productDTO).verifyComplete();

    }

    @Test
    public void getProductTest() {

        when(repository.findById("12345")).thenReturn(Mono.just(product));
        when(appUtils.entityToDTO(product)).thenReturn(productDTO);
        when(appUtils.dtoToEntity(productDTO)).thenReturn(product);

        final Mono<ProductDTO> result = service.getProduct("12345");

        StepVerifier.create(result).expectNext(productDTO).verifyComplete();

    }

//    @Test
//    public void deleteProduct() {
//
//        when(repository.deleteById("12345")).thenReturn(Mono.empty());
//        when(appUtils.entityToDTO(product)).thenReturn(productDTO);
//        when(appUtils.dtoToEntity(productDTO)).thenReturn(product);
//
//        System.out.println(appUtils.entityToDTO(product));
//
//        final Mono<ProductDTO> result = service.deleteProduct("12345");
//
//        StepVerifier.create(result).expectNext(productDTO).verifyComplete();
//    }

//    @Test
//    public void saveProductTest() {
//
//        when(repository.save(product)).thenReturn(Mono.just(product));
//        when(appUtils.entityToDTO(product)).thenReturn(productDTO);
//        when(appUtils.dtoToEntity(productDTO)).thenReturn(product);
//
//        repository.save(product).subscribe(System.out::println);
//        System.out.println(productDTO);
//
//        Mono<ProductDTO> result = service.saveProduct(Mono.just(productDTO));
//
//        result.subscribe(System.out::println);
//
//
//        StepVerifier.create(result).expectNext(productDTO).verifyComplete();
//
//    }


}