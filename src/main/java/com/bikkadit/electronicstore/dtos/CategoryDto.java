package com.bikkadit.electronicstore.dtos;


import com.bikkadit.electronicstore.entities.Product;
import com.bikkadit.electronicstore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(max=30 ,message = "Title didn't more than 30 character")
    private String title;

    @NotBlank
    @Size(min = 20,message = "Description must contain 20 character")
    private String description;

    @ImageNameValid
    private String coverImage;


    private List<ProductDto> products;
}
