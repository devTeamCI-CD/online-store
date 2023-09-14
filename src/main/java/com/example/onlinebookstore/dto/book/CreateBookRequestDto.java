package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotNull
    @NotBlank
    @Length(min = 1, max = 255)
    @Size(min = 1, max = 255)
    private String title;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 255)
    @Size(min = 1, max = 255)
    private String author;
    @NotNull
    @NotBlank
    @ISBN
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Size(min = 1, max = 255)
    private String description;
    @NotEmpty
    @NotNull
    private String coverImage;
    @NotEmpty
    private Set<Long> categories;
}
