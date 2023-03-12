package com.example.springmvcrest.controllers.v1;

import com.example.springmvcrest.api.v1.model.VendorDTO;
import com.example.springmvcrest.services.VendorService;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.example.springmvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

public class VendorControllerTest extends TestCase {

    public static final String NAME = "Joe";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;
    MockMvc mockMvc;

    VendorDTO vendor;
    VendorDTO vendor2;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();

        vendor = new VendorDTO("Joe", VendorController.BASE_URL + "/1");
        vendor2 = new VendorDTO("John", VendorController.BASE_URL + "/2");
    }

    public void testGetAllVendors() throws Exception {

        List<VendorDTO> vendorDTOS = Arrays.asList(vendor, vendor2);

        when(vendorService.getAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));


    }

    public void testGetVendorById() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor);

        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    public void testCreateNewVendor() throws Exception {
        when(vendorService.createNewVendor(vendor)).thenReturn(vendor);

        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    public void testUpdateVendor() throws Exception {

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(vendor);

        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    public void testPatchVendor() throws Exception {
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(vendor);

        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    public void testDeleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }
}