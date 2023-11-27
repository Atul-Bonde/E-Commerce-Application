package com.bikkadit.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
