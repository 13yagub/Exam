package com.example.exam.service;

import com.example.exam.dto.CustomerRequest;
import com.example.exam.dto.CustomerResponse;
import com.example.exam.dto.ChangePinRequest;
import com.example.exam.exception.InvalidRequestException;
import com.example.exam.exception.NotFoundException;
import com.example.exam.model.Customer;
import com.example.exam.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Random random = new Random();

    public CustomerResponse createCustomerWithCard(CustomerRequest request) {
        if (request.getFin() == null || request.getFin().isEmpty()) {
            throw new InvalidRequestException("FIN cannot be empty");
        }

        Customer customer = customerRepository.findByFinAndDeletedFalse(request.getFin())
                .orElseGet(() -> Customer.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .fin(request.getFin())
                        .build()
                );

        if (customer.getCardNumber() != null) {
            throw new InvalidRequestException("Customer already has a card");
        }

        customer.setCardNumber(generateCardNumber());
        customer.setCvv(generateCvv());
        customer.setExpireDate(LocalDate.now().plusYears(5));

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public CustomerResponse changePin(ChangePinRequest request) {
        if (request.getNewPin() == null || request.getNewPin().isEmpty()) {
            throw new InvalidRequestException("PIN cannot be empty");
        }

        Customer customer = customerRepository.findByCardNumberAndDeletedFalse(request.getCardNumber())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        customer.setPin(request.getNewPin());
        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public void deleteCustomer(String fin) {
        Customer customer = customerRepository.findByFinAndDeletedFalse(fin)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    private String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) sb.append(random.nextInt(10));
        return sb.toString();
    }

    private String generateCvv() {
        int cvv = random.nextInt(900) + 100;
        return String.valueOf(cvv);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fin(customer.getFin())
                .cardNumber(customer.getCardNumber())
                .cvv(customer.getCvv())
                .expireDate(customer.getExpireDate())
                .build();
    }
}