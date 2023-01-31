package com.example.LatihanRestApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor


public class TransferResponseDTO {
    Long transferID;
    String originUsername;
    String destinationUsername;
    Long amount;
    String status;

}
