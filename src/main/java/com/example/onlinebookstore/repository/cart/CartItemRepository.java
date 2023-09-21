package com.example.onlinebookstore.repository.cart;

import com.example.onlinebookstore.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("FROM CartItem c WHERE c.id = :cartItemId AND c.shoppingCart.id = :shoppingCartId")
    Optional<CartItem> findByIdAndShoppingcartId(Long cartItemId, Long shoppingCartId);
}
