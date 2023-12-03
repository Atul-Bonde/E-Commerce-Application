package com.bikkadit.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String productId;

    @Column(name = "product_title")
    private String title;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_price")
    private Double price;

    @Column(name = "product_discountPrice")
    private Double discountPrice;

    @Column(name = "product_quantity")
    private Integer quantity;

    @Column(name = "product_addedDate")
    private Date addedDate;

    @Column(name = "product_live")
    private boolean live;

    @Column(name = "product_stock")
    private boolean stock;
}
