package com.example.LatihanRestApi.models.repository;

import com.example.LatihanRestApi.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
   User findByUsername(String username);
   User findByNik(String nik);
}
