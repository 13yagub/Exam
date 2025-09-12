package com.example.exam.service;

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

    public Customer createCustomerWithCard(String firstName, String lastName, String fin, String pin) {
        if (fin == null || fin.isEmpty() || pin == null || pin.isEmpty()) {
            throw new RuntimeException("FIN and PIN cannot be empty");
        }

        Customer customer = customerRepository.findByFinAndDeletedFalse(fin)
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setFirstName(firstName);
                    newCustomer.setLastName(lastName);
                    newCustomer.setFin(fin);
                    return newCustomer;
                });

        if (customer.getCardNumber() != null) {
            throw new RuntimeException("Customer already has a card");
        }

        customer.setCardNumber(generateCardNumber());
        customer.setCvv(generateCvv());
        customer.setExpireDate(LocalDate.now().plusYears(5));
        customer.setPin(pin);

        return customerRepository.save(customer);
    }

    public Customer changePin(String cardNumber, String newPin) {
        if (newPin == null || newPin.isEmpty()) {
            throw new RuntimeException("PIN cannot be empty");
        }

        Customer customer = customerRepository.findByCardNumberAndDeletedFalse(cardNumber)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        customer.setPin(newPin);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String fin) {
        Customer customer = customerRepository.findByFinAndDeletedFalse(fin)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

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
}