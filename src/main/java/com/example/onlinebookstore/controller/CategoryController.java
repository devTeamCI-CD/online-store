package com.example.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Controller for managing book's categories")
@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController {

}
