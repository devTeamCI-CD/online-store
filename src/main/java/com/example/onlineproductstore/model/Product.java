package com.example.onlineproductstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String provider;
    @Column(nullable = false)
    private BigDecimal price;
    private String description;
    private String image;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Category> categories = new HashSet<>();

    public Product(Long id) {
        this.id = id;
    }
}
