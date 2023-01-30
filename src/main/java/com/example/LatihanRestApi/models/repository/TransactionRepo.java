package com.example.LatihanRestApi.models.repository;

import com.example.LatihanRestApi.models.entity.Transaction;

import org.springframework.data.repository.CrudRepository;

    public interface TransactionRepo extends CrudRepository<Transaction, Long> {

    }

