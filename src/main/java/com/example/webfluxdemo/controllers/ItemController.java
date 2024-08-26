package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.dto.ItemApplicationDTO;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
@Tag(name = "Item Operations", description = "Operations related to item management.")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @Operation( summary = "Retrieve all items", description = "Fetches a set of all items.")
    @ApiResponse(
            responseCode = "200",
            description = "Items retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
    )
    @GetMapping("/all")
    public Flux<Item> getAllItems() {
        return itemService.findAll();
    }

    @Operation(
            summary = "Retrieve item by ID",
            description = "Fetches the item details for the given task ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the item to retrieve",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public Mono<Item> getItemById(@PathVariable("id") Long id) {
        return itemService.findItemById(id);
    }

    @Operation(
            summary = "Create a new item",
            description = "Create a new item with the provided details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to create a new item.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ItemApplicationDTO.class),
                            examples =  @ExampleObject(
                                            name = "Item create example",
                                            summary = "Item create details",
                                            description = "Example data for create a new Item.",
                                            value = "{ \"name\": \"Item N\"}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Item successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                            name = "Item Created Example",
                                            summary = "Created Item",
                                            description = "Example of the response when a new item is successfully created.",
                                            value = "{ \"id\": 1, \"name\": \"Item N\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping("/create")
    public Mono<Item> createItem(@RequestBody ItemApplicationDTO itemAppMono) {
        return itemService.createItem(itemAppMono);
    }

    @Operation(
            summary = "Update an existing item",
            description = "Updates the item identified by the given item ID with the data provided.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The data to update an item.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ItemApplicationDTO.class),
                            examples =  @ExampleObject(
                                    name = "Item update example",
                                    summary = "Item update details",
                                    description = "Example data for update an Item.",
                                    value = "{ \"name\": \"Item N\"}"
                            )
                    )
            ),
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the item to update.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Item updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Item.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/update/{id}")
    public Mono<Item> updateItem(@PathVariable("id") Long id, @RequestBody ItemApplicationDTO itemAppMono) {
        return itemService.updateItem(id, itemAppMono);
    }

    @Operation(
            summary = "Delete an existing item",
            description = "Delete the element identified by the given ID.",
            parameters = @Parameter(
                    name = "id",
                    description = "The ID of the item to delete.",
                    required = true,
                    example = "1"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Item updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item not found"
            )
    })
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>>deleteItem(@PathVariable("id") Long id) {
        itemService.deleteItemById(id).subscribe();
        return Mono.just(ResponseEntity.noContent().build());
    }

}
