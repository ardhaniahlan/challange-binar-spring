package com.challangebinarspring.binarfud.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
@Where(clause = "deleted_date is null")
public class User extends AbstractDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    public String username;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;

}
