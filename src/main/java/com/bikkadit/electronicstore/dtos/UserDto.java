package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.validate.ImageNameValid;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;

    @Size(min=5 ,max = 15,message = "Invalid User Name !! Name Should Contain Minimum 3 And Maximum 15 Character .. ")
    private String name;

    @Email(message = "Enter Valid Email id ..")
    private String email;

    @NotBlank
//    @Pattern( regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}$",
//            message = "password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @NotBlank
    @Size(min=4,max=15,message = "Invalid Gender !!")
    private String gender;

    @NotBlank(message = "")
    private String about;

    @ImageNameValid
    private String imageName;
}
