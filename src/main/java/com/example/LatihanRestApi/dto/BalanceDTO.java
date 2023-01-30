package com.example.LatihanRestApi.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor


public class BalanceDTO {
    Long balance;

    Long transactionLimit;
}
