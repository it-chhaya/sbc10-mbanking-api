package co.istad.mbanking.features.fileupload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    FileUploadResponse upload(MultipartFile file);

}
