package com.challangebinarspring.binarfud.repository;

import com.challangebinarspring.binarfud.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query(value = "select c from Order c where c.id = :orderId")
    public Order getById(@Param("orderId") Long orderId);

}
