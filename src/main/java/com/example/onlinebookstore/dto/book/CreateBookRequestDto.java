package com.example.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateBookRequestDto {
    @NotNull
    @NotBlank
    @Length(min = 1, max = 255)
    private String title;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 255)
    private String author;
    @NotNull
    @NotBlank
    @ISBN
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @Length(min = 1, max = 255)
    private String description;
    @URL
    private String coverImage;
    @NotEmpty
    private Set<Long> categories;
}
