package com.example.lol.lol.services.product;

import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.model.ProductThumbnail;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    public void updateImage(ProductImage image, MultipartFile file) throws Exception;
    public void updateThumbnail(ProductThumbnail thumbnail, MultipartFile file) throws Exception;
}
