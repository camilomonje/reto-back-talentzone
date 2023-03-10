package com.sofka.retobackend.infraestructure.repository;

import com.sofka.retobackend.domain.collection.Shopping;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRepository extends ReactiveMongoRepository<Shopping, String > {
}
