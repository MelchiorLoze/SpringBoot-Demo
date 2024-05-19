package com.example.demo.list_product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListProductRepository extends JpaRepository<ListProduct, Long> {
}
