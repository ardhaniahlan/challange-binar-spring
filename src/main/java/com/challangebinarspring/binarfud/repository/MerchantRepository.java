package com.challangebinarspring.binarfud.repository;

import com.challangebinarspring.binarfud.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long>, JpaSpecificationExecutor<Merchant> {
    @Query(value = "select c from Merchant c where c.id = :merchantId")
    public Merchant getById(@Param("merchantId") Long merchantId);
}
