package com.ecommerce.library.dto;

import com.ecommerce.library.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank
    private String name;
    private String description;
    private Double costPrice;
    private Double salePrice;
    private Integer currentQuantity;
    private String image;
    private Category category;
    private Boolean is_deleted;
    private Boolean is_activated;
}
