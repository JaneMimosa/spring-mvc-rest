package com.example.springmvcrest.controllers.v1;

import com.example.springmvcrest.api.v1.model.CustomerDTO;
import com.example.springmvcrest.api.v1.model.CustomerListDTO;
import com.example.springmvcrest.repositories.CategoryRepository;
import com.example.springmvcrest.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CategoryRepository categoryRepository;

    public CustomerController(CustomerService customerService,
                              CategoryRepository categoryRepository) {
        this.customerService = customerService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomersById(@PathVariable Long id) {
        return new ResponseEntity<>(
                customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createNewCustomer(customerDTO), HttpStatus.CREATED);
    }
}
