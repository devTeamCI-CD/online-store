package com.example.onlinebookstore.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T, K> {
    Specification<T> build(K searchParams);
}
