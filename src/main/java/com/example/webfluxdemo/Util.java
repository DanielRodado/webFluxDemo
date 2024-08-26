package com.example.webfluxdemo;

import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Util {

    @Bean
    public CommandLineRunner initData(ItemRepository repository) {
        return args -> {
            Item item = new Item("Item 1");
            repository.save(item).subscribe();

            Item item2 = new Item("Item 2");
            repository.save(item2).subscribe();

            Item item3 = new Item("Item 3");
            repository.save(item3).subscribe();
        };
    }

}
