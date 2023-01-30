package com.example.LatihanRestApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PasswordChangeDTO {

    String username;

    String oldPassword;

    String newPassword;


}
