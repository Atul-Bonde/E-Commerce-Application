package com.bikkadit.electronicstore.payloads;

import lombok.*;

import javax.validation.constraints.Max;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse <T>{

     private List<T> content;

     private int pageNumber;

     private int pageSize;

     private long totalElements;

     private int totalPages;

     private boolean lastPage;



}
