package com.example.LatihanRestApi.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction")

    public class Transaction {


    @Id
    @Column(name="transaction_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="balance", nullable = false)
    private Long balance;


    @Column(name="debit")
    private Long debit;

    @Column(name="kredit")
    private Long kredit;

    @ManyToOne
    private User user;

    }
