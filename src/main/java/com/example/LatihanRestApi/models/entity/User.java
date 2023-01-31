package com.example.LatihanRestApi.models.entity;


import com.example.LatihanRestApi.constant.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 250, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name="nik", unique = true)
    private String nik;

    @Column(name="ban")
    private boolean ban;

    @Column(name="balance")
    private Long balance=0L;

    int counter = 0;

    @OneToMany(mappedBy = "user")
    List<Transaction> transactions;

    private Long maxTransaction = Constants.Max_Transaction_Amount;
}

