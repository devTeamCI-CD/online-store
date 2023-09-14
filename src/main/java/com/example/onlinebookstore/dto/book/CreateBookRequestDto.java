package com.example.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class CreateBookRequestDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String title;
    @NotNull
    @Size(min = 1, max = 255)
    private String author;
    @NotNull
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
}
