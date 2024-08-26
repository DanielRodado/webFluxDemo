package com.example.webfluxdemo.services;

import com.example.webfluxdemo.dto.ItemApplicationDTO;
import com.example.webfluxdemo.models.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {

    // Methods repository

    Flux<Item> findAll();

    Mono<Item> findItemById(Long id);

    Mono<Item> saveItem(Item item);

    // Methods Controller

    Mono<Item> createItem(ItemApplicationDTO itemApp);

    Mono<Item> updateItem(Long id, ItemApplicationDTO itemApp);

    Mono<Void> deleteItemById(Long id);

    // Methods of Validation


}
