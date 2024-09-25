package org.example.humanity.userFunctionality.items.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@Component
public class FileProcessorUtil {

    @Value("${application.images.directory.path}")
    private String uploadDirectory;

    public String processFileBeforeSave(MultipartFile file, String name) throws IOException {

        Path targetLocation;
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            targetLocation = Path.of(uploadDirectory, name+fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } else targetLocation = Path.of("");

        return targetLocation.toString();

    }


    public byte[] processFileBeforeSendingAsResponseToClientSide(String filePath) throws IOException {

        byte[] fileContent = new byte[0];
        if (StringUtils.hasText(filePath)) {
            try {
                Path imagePath = Path.of(filePath);
                fileContent = Files.readAllBytes(imagePath);
            }
            catch (Exception e){
                log.error("Exception occurred while processing file: {}", e.getMessage());
            }

        }
        return fileContent;
    }

}
