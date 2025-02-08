package com.contactvault.cvault.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.contactvault.cvault.helper.AppConstants;
import com.contactvault.cvault.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile contactImage, String fileName) throws IOException {


        // upload image to cloudinary
        byte[] data = new byte[contactImage.getInputStream().available()];
        contactImage.getInputStream().read(data);
        cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

        return getURLFromPublicId(fileName);

    }

    @Override
    public String getURLFromPublicId(String publicId) {

        return cloudinary.url().transformation(
                new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH).height(AppConstants.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstants.CONTACT_IMAGE_CROP)
                        .gravity(AppConstants.CONTACT_IMAGE_GRAVITY))
                .generate(publicId);
    }

}
