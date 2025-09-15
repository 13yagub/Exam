package com.example.exam.controller;

import com.example.exam.dto.CustomerRequest;
import com.example.exam.dto.CustomerResponse;
import com.example.exam.dto.ChangePinRequest;
import com.example.exam.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/create-card")
    public CustomerResponse createCustomerWithCard(@RequestBody CustomerRequest request) {
        return customerService.createCustomerWithCard(request);
    }

    @PostMapping("/change-pin")
    public CustomerResponse changePin(@RequestBody ChangePinRequest request) {
        return customerService.changePin(request);
    }

    @DeleteMapping("/delete/{fin}")
    public void deleteCustomer(@PathVariable String fin) {
        customerService.deleteCustomer(fin);
    }
}