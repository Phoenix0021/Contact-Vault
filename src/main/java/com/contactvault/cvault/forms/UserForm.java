package com.contactvault.cvault.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Minimum 3 Characters is required")
    private String name;

    @NotBlank(message = "Email is required") @Email(message = "Invalid Email Adderess")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Required")
    private String about;
    @Size(min=10, max = 12, message = "Invalid Number")
    private String phoneNumber;

}
