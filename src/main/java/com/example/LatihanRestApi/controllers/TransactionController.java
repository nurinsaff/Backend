package com.example.LatihanRestApi.controllers;


import com.example.LatihanRestApi.constant.Constants;
import com.example.LatihanRestApi.dto.TopUpDTO;
import com.example.LatihanRestApi.dto.TransferRequestDTO;
import com.example.LatihanRestApi.dto.TransferResponseDTO;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.TransactionRepo;
import com.example.LatihanRestApi.services.TransactionService;
import com.example.LatihanRestApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepo transactionRepo;

    @PostMapping("/create")
    public ResponseEntity<Object> createTransaction(@RequestBody TransferRequestDTO transferRequestDTO) {
//        String password = user.getPassword();
        User user = userService.findByUsername(transferRequestDTO.getUsername());

        if (!userService.findUsername(transferRequestDTO.getUsername())) {
            return new ResponseEntity<>("username not found", HttpStatus.BAD_REQUEST);
        }
        try {
            if (!userService.validatePassword(user, transferRequestDTO.getPassword())){
                return new ResponseEntity<>("invalid password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if (!userService.findUsername(transferRequestDTO.getDestinationUsername())) {
            return new ResponseEntity<>("destination user not found", HttpStatus.BAD_REQUEST);
        }
        if(transferRequestDTO.getAmount() > user.getMaxTransaction()){
            return new ResponseEntity<>("transaction limit exceeded", HttpStatus.BAD_REQUEST);
        }
        if(!transactionService.minimalBalanceCheck(transferRequestDTO.getUsername(), transferRequestDTO.getAmount())){
            return new ResponseEntity<>("not enough balance", HttpStatus.BAD_REQUEST);
        }
        if(!transactionService.minimalTransactionCheck(transferRequestDTO.getAmount())){
            return new ResponseEntity<>("minimum transfer amount is " + Constants.Min_Transaction_Amount, HttpStatus.BAD_REQUEST);
        }

        TransferResponseDTO transferResponseDTO = transactionService.createTransaction(transferRequestDTO);
        return new ResponseEntity<>(transferResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/topup")
    public ResponseEntity<Object> topup(@RequestBody TopUpDTO topUpDTO) {
        User user = userService.findByUsername(topUpDTO.getUsername());

        if (!userService.findUsername(topUpDTO.getUsername())) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }

        try {
            if (!userService.validatePassword(user, topUpDTO.getPassword())) {
                return new ResponseEntity<>("invalid password", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (Constants.Max_Topup < topUpDTO.getAmount()) {
            return new ResponseEntity<>("max topup exceeded", HttpStatus.BAD_REQUEST);
        }
        if (!transactionService.maxBalanceCheck(topUpDTO.getUsername(), topUpDTO.getAmount())) {
            return new ResponseEntity<>("max balance exceeded", HttpStatus.BAD_REQUEST);
        }

        transactionService.addNominal(topUpDTO.getUsername(), topUpDTO.getAmount());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }



}

