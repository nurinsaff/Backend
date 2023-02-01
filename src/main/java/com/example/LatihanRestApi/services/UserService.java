package com.example.LatihanRestApi.services;

import com.example.LatihanRestApi.constant.Constants;
import com.example.LatihanRestApi.dto.BalanceDTO;
import com.example.LatihanRestApi.dto.InfoDTO;
import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.models.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@Transactional

public class UserService {


    @Autowired
    private UserRepo userRepo;

    public void removeOne(Long id) {
        userRepo.deleteById(id);
    }

/*    public User find(Long id) {
       return userRepo.findById(id).get() ;
    }

 */

    public List<User> findAll(){

        return (List<User>) userRepo.findAll();
    }

    public Boolean findUsername(String username){

        return userRepo.findByUsername(username) != null;
    }

    public Boolean validateFormatPass(String password){

        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{10,}$";

        return password.matches(regex);
    }

    public void createUser(User user){

        userRepo.save(user);
    }

    public InfoDTO getInfo(String username){
        User user = userRepo.findByUsername(username);
        return new InfoDTO(user.getUsername(), user.getNik());
    }

    public BalanceDTO getBalance(String username){
        User user = userRepo.findByUsername(username);
        Long limit;

        if(user.getNik()==null){
            limit=1000000L;
        } else {
            limit=5000000L;
        }

        return new BalanceDTO(user.getBalance(), limit);
    }


    public void Ban(String username){
        User user = userRepo.findByUsername(username);
        user.setBan(true);
        userRepo.save(user);
    }

    public Boolean isBanned(String username){
        User user = userRepo.findByUsername(username);
        return user.isBan();
    }

    public void Unban(String username){
        User user = userRepo.findByUsername(username);
        user.setCounter(0);
        user.setBan(false);
        userRepo.save(user);
    }

    public Boolean findKtp(String nik){

        return userRepo.findByNik(nik) != null;
    }

    public Boolean validateFormatNik(String nik){

        String regex = "\\d+";

        return nik.matches(regex);
    }


    public void addKtp(String username, String nik){
        User user = userRepo.findByUsername(username);
        user.setNik(nik);
        user.setMaxTransaction(Constants.Max_Transaction_Amount_With_KTP);
        userRepo.save(user);
    }

    public Boolean validatePassword(User user, String oldPassword) throws Exception {
//        String password = user.getPassword();

        if(!user.getPassword().equals(oldPassword)) {
            user.setCounter(user.getCounter() + 1);
            if (user.getCounter() == (Constants.Max_Try)){
               Ban(user.getUsername());
                throw new Exception("User is banned");
            }
            return false;
        }
        return true;
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws Exception{
        User user = userRepo.findByUsername(username);

        if(!validatePassword(user, oldPassword)){
            throw new Exception("password invalid");
        }

        if(!validateFormatPass(newPassword)){
            throw new Exception("invalid password format");
        }
            user.setPassword(newPassword);
            userRepo.save(user);
    }


    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
