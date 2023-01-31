package com.example.LatihanRestApi.models.repository;

import com.example.LatihanRestApi.models.entity.Transaction;

import com.example.LatihanRestApi.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Long> {

        List<Transaction> findByDate(LocalDate date);

    }

