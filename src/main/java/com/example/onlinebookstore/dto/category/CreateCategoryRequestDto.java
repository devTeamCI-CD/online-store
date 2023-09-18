package com.example.onlinebookstore.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    private String name;
    @Length(min = 1, max = 255)
    private String description;
}
