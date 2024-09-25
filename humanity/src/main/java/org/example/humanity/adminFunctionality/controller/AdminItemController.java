package org.example.humanity.adminFunctionality.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.humanity.adminFunctionality.service.AdminService;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.items.service.ItemServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class AdminItemController {

    private final AdminService adminService;
    private final ItemServices itemServices;

    @GetMapping("get_all_items")
    public List<ItemResponse> getAllItems() throws IOException {

        return adminService.getAllItems();
    }

    @GetMapping("get_total_number_of_items")
    public String getNumberOfItems() throws IOException {

        return adminService.getNumberOfItems();
    }

    @GetMapping("get_item_by_id")
    public ItemResponse getItemById(@PathParam("id") Long id) throws IOException {

        return itemServices.getAnItem(id);
    }

    @GetMapping("get_item_by_name")
    public ItemResponse getItemByName(@PathParam("name") String name) throws IOException {

        return adminService.getAnItemByName(name);
    }
}
