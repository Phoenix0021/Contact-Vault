package com.contactvault.cvault.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {
    
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Phone is required")
    //pattern for ten digit phonenumber
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")

    @NotBlank(message = "Phone is required")
    private String phoneNumber;
    @NotBlank(message = "Message is required")
    private String address;
     private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;

    private MultipartFile contactImage;

    private String picture;

    
 


    
}
