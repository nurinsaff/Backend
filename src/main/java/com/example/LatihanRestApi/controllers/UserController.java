package com.example.LatihanRestApi.controllers;

import com.example.LatihanRestApi.dto.BalanceDTO;
import com.example.LatihanRestApi.dto.InfoDTO;
import com.example.LatihanRestApi.dto.KtpAddDTO;
import com.example.LatihanRestApi.dto.PasswordChangeDTO;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.UserRepo;
import com.example.LatihanRestApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/registration")
    public ResponseEntity<Object> create(@Valid @RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (userService.findUsername(username)) {
            return new ResponseEntity<>("username taken", HttpStatus.BAD_REQUEST);
        }
        if (!userService.validateFormatPass(password)) {
            return new ResponseEntity<>("invalid password format", HttpStatus.BAD_REQUEST);
        }
        userService.createUser(user);

        return new ResponseEntity<>("200 : OK - account saved", HttpStatus.OK);
    }

    @GetMapping("{username}/getinfo")
    public ResponseEntity<Object> info(@PathVariable String username) {
        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        InfoDTO info = userService.getInfo(username);
        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("{username}/getbalance")
    public ResponseEntity<Object> balance(@PathVariable String username) {
        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        BalanceDTO balance = userService.getBalance(username);
        return new ResponseEntity<Object>(balance, HttpStatus.OK);
    }

    @PutMapping("{username}/ban")
    public ResponseEntity<Object> ban(@PathVariable String username) {
        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        userService.Ban(username);
        return new ResponseEntity<>("User banned", HttpStatus.OK);
    }

    @PutMapping("{username}/unban")
    public ResponseEntity<Object> unban(@PathVariable String username) {
        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        userService.Unban(username);
        return new ResponseEntity<>("user unban", HttpStatus.OK);
    }

    @PutMapping("{username}/addktp")
    public ResponseEntity<Object> addktp(@PathVariable String username, @RequestBody KtpAddDTO addKtpDTO) {

        if(!userService.validateFormatNik(addKtpDTO.getNik())){
            return new ResponseEntity<>("Format invalid", HttpStatus.BAD_REQUEST);
        }
        if (!userService.findUsername(username)) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        if (userService.findKtp(username)) {
            return new ResponseEntity<>("ktp has been used by other user", HttpStatus.BAD_REQUEST);
        }

        userService.addKtp(username, addKtpDTO.getNik());

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Object> changepassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        if (!userService.findUsername(passwordChangeDTO.getUsername())) {
            return new ResponseEntity<>("user not found", HttpStatus.BAD_REQUEST);
        }
        try {
            userService.changePassword(passwordChangeDTO.getUsername(), passwordChangeDTO.getOldPassword(), passwordChangeDTO.getNewPassword());
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }


}
