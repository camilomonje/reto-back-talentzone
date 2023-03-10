package com.sofka.retobackend.utils;

import com.sofka.retobackend.domain.collection.Product;
import com.sofka.retobackend.domain.collection.Shopping;
import com.sofka.retobackend.domain.dto.ProductDTO;
import com.sofka.retobackend.domain.dto.ShoppingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {

    public ProductDTO entityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public Product dtoToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }

    public ShoppingDTO entityToDTOShopping(Shopping shopping) {
        ShoppingDTO shoppingDTO = new ShoppingDTO();
        BeanUtils.copyProperties(shopping, shoppingDTO);
        return shoppingDTO;
    }
    public Shopping dtoToEntityShopping(ShoppingDTO shoppingDTO) {
        Shopping shopping = new Shopping();
        BeanUtils.copyProperties(shoppingDTO, shopping);
        return shopping;
    }
}
