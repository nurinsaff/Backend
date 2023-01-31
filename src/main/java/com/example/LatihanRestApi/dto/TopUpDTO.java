package com.example.LatihanRestApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopUpDTO {
    String username;
    String password;
    Long amount;
}
