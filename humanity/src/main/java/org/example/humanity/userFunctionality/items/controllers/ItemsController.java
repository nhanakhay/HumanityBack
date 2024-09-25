package org.example.humanity.userFunctionality.items.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.humanity.userFunctionality.items.pojo.ItemRequest;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.items.repo.ItemRepository;
import org.example.humanity.userFunctionality.items.service.ItemServices;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user/item")
@RequiredArgsConstructor
public class ItemsController {

    private final ItemRepository itemRepository;
    private final ItemServices itemServices;


    @PostMapping("create_item")
    public ItemResponse createItem(@ModelAttribute ItemRequest request) throws IOException {

        return itemServices.createItem(request);
    }

    @GetMapping("get_all_items")
    public List<ItemResponse> getAllItemsByUsername(@PathParam("userName") String userName){

        return itemServices.getAllItemsByUsername(userName);
    }

    @GetMapping("get_item_by_id")
    public ItemResponse getItemById(@PathParam("id") Long id) throws IOException {

        return itemServices.getAnItem(id);
    }

    @PutMapping("update_item_by_id")
    public ItemResponse updateAnItem(@ModelAttribute ItemRequest request, @PathParam("id") Long id) throws IOException {

        return itemServices.updateAnItem(request,id);
    }


    //TODO not tested
    @DeleteMapping("delete_item")
    public String deleteItem(@PathParam("id") Long id) throws IOException {

        return itemServices.deleteItem(id);
    }



}
