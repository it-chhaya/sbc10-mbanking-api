package co.istad.mbanking.features.fileupload;

import lombok.Builder;

@Builder
public record FileUploadResponse(

        String name,

        String uri,

        String contentType,

        Long size

) {
}