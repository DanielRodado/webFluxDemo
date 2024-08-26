package com.example.webfluxdemo.services.implement;

import com.example.webfluxdemo.dto.ItemApplicationDTO;
import com.example.webfluxdemo.exceptions.InvalidItemNameException;
import com.example.webfluxdemo.exceptions.NotFoundItemException;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import com.example.webfluxdemo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Flux<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Mono<Item> findItemById(Long id) {
        return itemRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundItemException("Item not found.")));
    }

    @Override
    public Mono<Item> saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Mono<Item> createItem(ItemApplicationDTO itemApp) {
        return saveItem(new Item(itemApp.name()));
    }

    @Override
    public Mono<Item> updateItem(Long id, ItemApplicationDTO itemApp) {
        return findItemById(id).flatMap(existingItem -> {
            existingItem.setName(itemApp.name());
            return saveItem(existingItem);
        });
    }

    @Override
    public Mono<Void> deleteItemById(Long id) {
        return findItemById(id).flatMap(existingItem -> itemRepository.delete(existingItem));
    }

}
