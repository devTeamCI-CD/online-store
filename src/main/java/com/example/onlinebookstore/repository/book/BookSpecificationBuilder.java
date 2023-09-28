package com.example.onlinebookstore.repository.book;

import com.example.onlinebookstore.dto.book.BookSearchParameters;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.SpecificationBuilder;
import com.example.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParams) {
        Specification<Book> specification = Specification.where(null);
        if (isParameterPresent(searchParams.author())) {
            specification = addSpecification(specification, "author", searchParams.author());
        }
        if (isParameterPresent(searchParams.isbn())) {
            specification = addSpecification(specification, "isbn", searchParams.isbn());
        }
        if (isParameterPresent(searchParams.title())) {
            specification = addSpecification(specification, "title", searchParams.title());
        }
        return specification;
    }

    private boolean isParameterPresent(String[] param) {
        return param != null && param.length > 0;
    }

    private Specification<Book> addSpecification(Specification<Book> specification,
                                                 String key, String[] params) {
        return specification.and(bookSpecificationProviderManager
                .getSpecificationProvider(key).getSpecification(params));
    }
}
