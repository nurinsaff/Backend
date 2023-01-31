package com.example.LatihanRestApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TransferRequestDTO {
    String username;
    String password;
    String destinationUsername;
    Long amount;
}
