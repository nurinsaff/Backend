package com.example.LatihanRestApi.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
@NoArgsConstructor

public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    Long amount;

    Long balanceBefore;

    Long balanceAfter;

    String status;

    LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public Transaction(String username, Long amount, Long balanceBefore, Long balanceAfter, String status, LocalDate date, User user) {
        this.username = username;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.status = status;
        this.date = date;
        this.user = user;
    }
}
