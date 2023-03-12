package com.example.springmvcrest.controllers.v1;


import com.example.springmvcrest.api.v1.model.CustomerDTO;
import com.example.springmvcrest.services.CustomerService;
import junit.framework.TestCase;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.springmvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest extends TestCase {

    public static final String NAME = "Doe";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    public void testGetAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Joe");
        customer1.setLastName("Doe");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");

        List<CustomerDTO> customerDTOS = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void testGetCustomersById() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Joe");
        customer1.setLastName(NAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);

        mockMvc.perform(get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", equalTo(NAME)));
    }

    public void testCreateNewCustomer() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Joe");
        customer1.setLastName("Doe");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer1.getFirstName());
        returnDTO.setLastName(customer1.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer1)).thenReturn(returnDTO);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastName", equalTo(NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));


    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Joe");
        customer1.setLastName("Doe");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer1.getFirstName());
        returnDTO.setLastName(customer1.getLastName());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Joe")))
                .andExpect(jsonPath("$.lastName", equalTo(NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Joe");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer1.getFirstName());
        returnDTO.setLastName("Smith");
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Joe")))
                .andExpect(jsonPath("$.lastName", equalTo("Smith")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {

        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}