package com.blog.Blogging_Application_API.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Minimum Size of Category Title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Minimum Size of Category Description is 10")
    private String categoryDescription;
}
