package com.bikkadit.electronicstore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String productId;

    @NotBlank
    @Size(max = 15 ,message = "Title didn't contain character more than 15")
    private String title;

    @NotBlank
    @Size(min = 20,message = "Description must contain 20 character")
    private String description;


    private Double price;

    private Double discountPrice;

    private Integer quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;
}
