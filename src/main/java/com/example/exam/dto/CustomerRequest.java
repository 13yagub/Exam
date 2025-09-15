package com.example.exam.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    String firstName;
    String lastName;
    String fin;
}