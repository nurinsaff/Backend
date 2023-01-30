package com.example.LatihanRestApi.dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class TransferDTO {
    String transferID;
    String originUsername;
    String destinationUsername;
    String amount;
    String status;

}
