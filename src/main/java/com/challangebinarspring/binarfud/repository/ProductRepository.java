package com.challangebinarspring.binarfud.repository;

import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "select c from Product c where c.id = :productId")
    public Product getById(@Param("productId") Long productId);

}
