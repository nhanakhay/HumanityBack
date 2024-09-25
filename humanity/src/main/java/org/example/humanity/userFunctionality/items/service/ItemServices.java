package org.example.humanity.userFunctionality.items.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.example.humanity.userFunctionality.items.entities.Items;
import org.example.humanity.userFunctionality.items.pojo.ItemRequest;
import org.example.humanity.userFunctionality.items.pojo.ItemResponse;
import org.example.humanity.userFunctionality.items.repo.ItemRepository;
import org.example.humanity.userFunctionality.items.utils.file.FileProcessorUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServices {

    private final FileProcessorUtil fileProcessorUtil;
    private final ItemRepository repo;


    public ItemResponse createItem(ItemRequest request) throws IOException {
        log.info("function accessed");

        var item = Items.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .phoneNumber(request.getPhone())
                .username(request.getUserName())
                .category(request.getCategory())
                .email(request.getEmail())
                .yearsUsed(request.getYearsUsed())
                .image(fileProcessorUtil.processFileBeforeSave(request.getPicture(), request.getUserName()))
                .build();

        Items savedItem = repo.save(item);
        log.info("Item created: {}", savedItem);

        return ItemResponse.builder()
                .id(savedItem.getId())
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .location(savedItem.getLocation())
                .phone(savedItem.getPhoneNumber())
                .category(String.valueOf(savedItem.getCategory()))
                .email(savedItem.getEmail())
                .yearsUsed(String.valueOf(savedItem.getYearsUsed()))
                .picture(savedItem.getImage())
                .username(savedItem.getUsername())
                .build();

    }

    public List<ItemResponse> getAllItemsByUsername(String userName) {
        // Ensure your repository method returns a List<Items>
        List<Items> items = repo.findByUsername(userName);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException("Items under the provided user name do not exist");
        }

        // Map each Items object to an ItemResponse using the static method
        return items.stream()
                .map(ItemResponse::mapToItemResponse)
                .collect(Collectors.toList());
    }




    public ItemResponse getAnItem(Long id) throws IOException {
        Items item = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with the provided id does not exist"));

        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .location(item.getLocation())
                .phone(item.getPhoneNumber())
                .category(String.valueOf(item.getCategory()))
                .email(item.getEmail())
                .yearsUsed(String.valueOf(item.getYearsUsed()))
                .picture(item.getImage())
                .username(item.getUsername())
                .build();
    }


    public ItemResponse updateAnItem(ItemRequest request, Long id) throws IOException {
        // Step 1: Retrieve the existing item
        Items existingItem = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        // Step 2: Update the fields of the existing item with the values from the request
        if (request.getName() != null) {
            existingItem.setName(request.getName());
        }
        if (request.getDescription() != null) {
            existingItem.setDescription(request.getDescription());
        }
        if (request.getPhone() != null) {
            existingItem.setPhoneNumber(request.getPhone());
        }
        if (request.getLocation() != null) {
            existingItem.setLocation(request.getLocation());
        }
        if (request.getPicture() != null) {
            // Assuming you're handling the image as a MultipartFile
            String imagePath = fileProcessorUtil.processFileBeforeSave(request.getPicture(), request.getUserName()); // Method to save the image and return the path
            existingItem.setImage(imagePath);
        }

        // Step 3: Save the updated item back to the database
        Items updatedItem = repo.save(existingItem);

        // Step 4: Map the updated item to an ItemResponse and return it
        return ItemResponse.mapToItemResponse(updatedItem);
    }



    public String deleteItem(Long id) throws IOException {
        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item with the provided id does not exist"));
        repo.deleteItem(id);
        return "item deleted successfully";

    }
}
