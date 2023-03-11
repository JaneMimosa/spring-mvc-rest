package com.example.springmvcrest.api.v1.mapper;

import com.example.springmvcrest.api.v1.model.CustomerDTO;
import com.example.springmvcrest.domain.Customer;
import junit.framework.TestCase;

import java.util.Optional;

public class CustomerMapperTest extends TestCase {

    public static final String NAME = "Joe";
    public static final long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public void testCustomerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(NAME);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(NAME, customerDTO.getFirstName());
    }
}