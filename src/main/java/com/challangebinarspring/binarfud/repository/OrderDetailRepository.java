package com.challangebinarspring.binarfud.repository;

import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
    @Query(value = "select c from OrderDetail c where c.id = :orderDetailId")
    public OrderDetail getById(@Param("orderDetailId") Long orderDetailId);
}
