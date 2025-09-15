package com.example.exam.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    String firstName;
    String lastName;
    String fin;
    String cardNumber;
    String cvv;
    LocalDate expireDate;
}