package com.example.exam.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePinRequest {
    String cardNumber;
    String newPin;
}