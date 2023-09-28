package com.example.onlinebookstore.repository.order;

import com.example.onlinebookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart s LEFT JOIN FETCH s.cartItems ci "
            + "LEFT JOIN FETCH ci.book WHERE s.user.id = :userId")
    Optional<ShoppingCart> findByUserId(Long userId);
}
