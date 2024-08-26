package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.dto.ItemApplicationDTO;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public Flux<Item> getAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable("id") Long id) {
        return itemService.findItemById(id);
    }

    @PostMapping("/create")
    public Mono<Item> createItem(@RequestBody ItemApplicationDTO itemAppMono) {
        return itemService.createItem(itemAppMono);
    }

    @PutMapping("/update/{id}")
    public Mono<Item> updateItem(@PathVariable("id") Long id, @RequestBody ItemApplicationDTO itemAppMono) {
        return itemService.updateItem(id, itemAppMono);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>>deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItemById(id).subscribe();
        return Mono.just(ResponseEntity.noContent().build());
    }

}
