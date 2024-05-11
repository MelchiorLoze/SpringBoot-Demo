package com.example.demo.list_product;

import com.example.demo.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "list_products")
@NoArgsConstructor
public class ListProduct {

    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @PositiveOrZero
    @Column(name = "amount")
    private Integer amount;

    public ListProduct(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public ListProduct(Product product) {
        this(product, 1);
    }
}
