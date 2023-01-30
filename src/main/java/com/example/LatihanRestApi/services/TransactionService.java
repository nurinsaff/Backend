package com.example.LatihanRestApi.services;

import com.example.LatihanRestApi.constant.Constants;
import com.example.LatihanRestApi.dto.BalanceDTO;
import com.example.LatihanRestApi.dto.TransferDTO;
import com.example.LatihanRestApi.models.entity.Transaction;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.TransactionRepo;
import com.example.LatihanRestApi.models.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public void createTransaction(Transaction transaction){
        transactionRepo.save(transaction);
    }

    public TransferDTO transfer(String fromUser, String toUser, String nominal){
        User user = userRepo.findByUsername(username);
        user.setBan(true);
        userRepo.save(user);
        return new TransferDTO(user.getBalance(), limit);
    }

    public void addNominal(String username, Long nominal){
        User user = userRepo.findByUsername(username);
        user.setBalance(nominal);
        TransactionRepo.save();
    }


    public Boolean settled();





}
