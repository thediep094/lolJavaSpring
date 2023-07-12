package com.example.lol.lol.services.product;

import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.model.ProductThumbnail;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor

public class ImageUploadServiceIml implements ImageUploadService{
    private final String bucketName;
    private final Storage storage;

    public void updateImage(ProductImage image, MultipartFile file) throws IOException {
        // Upload the image file to Firebase Storage
        String filename = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, filename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Update the image URL in the ProductImage object
        image.setName(filename);
    }

    public void updateThumbnail(ProductThumbnail thumbnail, MultipartFile file) throws IOException {
        // Upload the thumbnail file to Firebase Storage
        String filename = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, filename);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Update the thumbnail URL in the ProductThumbnail object
        thumbnail.setName(filename);
    }
}
