package com.bikkadit.electronicstore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "categories")
@Builder
public class Category {

     @Id
     private String categoryId;

     @Column(name="category_title")
     private String title;

     @Column(name="category_description")
     private String description;

     @Column(name="category_image")
     private String coverImage;

     @OneToMany(mappedBy = "category")
     private List<Product> products;

}
