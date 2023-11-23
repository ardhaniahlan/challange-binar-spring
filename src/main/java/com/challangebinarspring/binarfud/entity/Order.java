package com.challangebinarspring.binarfud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
@Where(clause = "deleted_date is null")
public class Order extends AbstractDate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "order_time")
    public Date orderTime;

    @Column(name = "destnation_address")
    public String destinationAddress;

    @Column(name = "complated")
    public boolean complated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
