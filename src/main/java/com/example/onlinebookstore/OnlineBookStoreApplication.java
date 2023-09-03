package com.example.onlinebookstore;

import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book harryPotter = new Book();
            harryPotter.setTitle("Harry Potter");
            harryPotter.setAuthor("Joan Roaling");
            harryPotter.setIsbn("9780751565355");
            harryPotter.setPrice(BigDecimal.valueOf(299L));

            bookService.save(harryPotter);
            bookService.findAll().forEach(System.out::println);
        };
    }
}
