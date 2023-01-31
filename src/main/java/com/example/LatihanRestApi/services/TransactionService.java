package com.example.LatihanRestApi.services;

import com.example.LatihanRestApi.constant.Constants;
import com.example.LatihanRestApi.dto.TransferRequestDTO;
import com.example.LatihanRestApi.dto.TransferResponseDTO;
import com.example.LatihanRestApi.models.entity.Transaction;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.TransactionRepo;
import com.example.LatihanRestApi.models.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional

public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public TransferResponseDTO createTransaction(TransferRequestDTO transferRequestDTO) {

        String originUsername = transferRequestDTO.getUsername();
        String destinationUsername = transferRequestDTO.getDestinationUsername();
        Long amount = transferRequestDTO.getAmount();

        User originUser = userRepo.findByUsername(originUsername);
        User destinationUser = userRepo.findByUsername(destinationUsername);

        Long balanceOriginBefore = originUser.getBalance();
        Long balanceDestinationBefore = destinationUser.getBalance();

        Long balanceOriginAfter = balanceOriginBefore - Math.round(amount * Constants.Transaction_Tax + amount);
        Long balanceDestinationAfter = balanceDestinationBefore + amount;

        originUser.setBalance(balanceOriginAfter);
        destinationUser.setBalance(balanceDestinationAfter);

        LocalDate localDate = LocalDate.now();

        userRepo.saveAll(List.of(originUser, destinationUser));

        Transaction transactionOrigin = new Transaction(originUsername, amount, balanceOriginBefore
                , balanceOriginAfter, "Settled", localDate, originUser);
        Transaction transactionDestination = new Transaction(destinationUsername, amount, balanceDestinationBefore
                , balanceDestinationAfter, "Settled", localDate, destinationUser);

        transactionRepo.saveAll(List.of(transactionOrigin, transactionDestination));

        return new TransferResponseDTO(transactionOrigin.getId(), originUsername, destinationUsername, amount, "settled");
    }

    public Boolean minimalBalanceCheck(String username, Long amount) {

        User originUser = userRepo.findByUsername(username);
        Long balanceOriginAfter = originUser.getBalance() - Math.round(amount * Constants.Transaction_Tax + amount) ;

        if (balanceOriginAfter < Constants.Min_Balance) {
            return false;
        }
        return true;
    }

    public Boolean minimalTransactionCheck(Long amount){

        if(amount < Constants.Min_Transaction_Amount){
            return false;
        }
            return true;
    }

    public void addNominal(String username, Long amount){
        User user = userRepo.findByUsername(username);
        Long nominal = user.getBalance();

        Long balance = nominal + amount;
        user.setBalance(balance);
        userRepo.save(user);

        Transaction transaction = new Transaction(username, amount, nominal, balance, "Settled", LocalDate.now(), user);
        transactionRepo.save(transaction);
    }

    public Boolean maxBalanceCheck(String username, Long amount) {
        User user = userRepo.findByUsername(username);
        Long balanceOriginAfter = user.getBalance() + Math.round(amount * Constants.Transaction_Tax + amount);

        if (balanceOriginAfter > Constants.Max_Balance) {
            return false;
        }
        return true;
    }

}

