package com.example.onlineproductstore.repository.order;

import com.example.onlineproductstore.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            FROM OrderItem i
            WHERE i.order.id = :orderId
            AND i.order.user.id = :userId""")
    List<OrderItem> findAllByOrderIdAndCurrentUser(Long orderId, Pageable pageable, Long userId);

    @Query("""
            FROM OrderItem i
            WHERE i.id = :id
            AND i.order.id = :orderId
            AND i.order.user.id = :userId""")
    Optional<OrderItem> findByIdAndOrderIdAndCurrentUser(Long orderId, Long id, Long userId);
}
