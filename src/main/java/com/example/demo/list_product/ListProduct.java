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
    @Column(name = "amount")
    private Integer amount;

    public ListProduct(Product product, Integer amount) {
        amount = amount == null ? 1 : amount;
        this.product = product;
        this.amount = amount;
    }
}
