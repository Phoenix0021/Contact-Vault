package com.contactvault.cvault.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

     String uploadImage(MultipartFile contactImage, String fileName) throws IOException;
     String getURLFromPublicId(String publicId);

}
