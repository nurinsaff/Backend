package com.example.LatihanRestApi.controllers;


import com.example.LatihanRestApi.constant.Constants;
import com.example.LatihanRestApi.dto.BalanceDTO;
import com.example.LatihanRestApi.dto.InfoDTO;
import com.example.LatihanRestApi.dto.KtpAddDTO;
import com.example.LatihanRestApi.dto.PasswordChangeDTO;
import com.example.LatihanRestApi.models.entity.Transaction;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.TransactionRepo;
import com.example.LatihanRestApi.models.repository.UserRepo;
import com.example.LatihanRestApi.services.TransactionService;
import com.example.LatihanRestApi.services.UserService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private User user;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody Transaction transaction) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("username not found", HttpStatus.BAD_REQUEST);
        }
        if (!userService.validateFormatPass(password)) {
            if (userService.validatePassword(password))
                transactionService.createTransaction(transaction);
            return new ResponseEntity<>("invalid password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("200 : OK - account saved", HttpStatus.OK);
    }

    @PostMapping("/topup")
    public ResponseEntity<Object> topup(@PathVariable String username, @RequestBody Long nominal ){

            Long amount = Transaction.balance(username) + nominal;

            if (!userService.findUsername(username)) {
                return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
            }
            if (Constants.Max_Topup >= nominal) {
                return new ResponseEntity<>("max topup exceeded", HttpStatus.BAD_REQUEST);
            }
            if (Constants.Max_Balance <= amount)
                return new ResponseEntity<>("max balance exceeded", HttpStatus.BAD_REQUEST);

            transactionService.addNominal(username, nominal);

            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }



  */

}
