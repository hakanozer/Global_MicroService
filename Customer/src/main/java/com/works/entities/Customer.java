package com.works.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String name;
    @Column(unique = true)
    private String email;
    @Column(length = 500)
    private String password;

}
