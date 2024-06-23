package co.istad.mbanking.features.fileupload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;

    @Override
    public FileUploadResponse upload(MultipartFile file) {

        String extension = file.getContentType().split("/")[1];
        String newFileName = String.format("%s.%s",
                UUID.randomUUID(),
                extension);

        log.info("Extension: {}", extension);
        log.info("New File Name: {}", newFileName);

        Path path = Paths.get(serverPath + newFileName);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed");
        }

        return FileUploadResponse.builder()
                .name(newFileName)
                .uri(baseUri + newFileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
    }

}
