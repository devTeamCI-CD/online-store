package com.example.onlineproductstore.repository.product;

import com.example.onlineproductstore.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    @Query("FROM Product p LEFT JOIN FETCH p.categories c WHERE c.id = :categoryId")
    List<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("FROM Product p LEFT JOIN FETCH p.categories WHERE p.id = :id")
    Optional<Product> findByIdWithCategories(Long id);

    @Query("FROM Product p LEFT JOIN FETCH p.categories")
    List<Product> findAllWithCategories(Pageable pageable);
}
