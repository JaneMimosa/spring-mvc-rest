package com.example.springmvcrest.services;

import com.example.springmvcrest.api.v1.mapper.VendorMapper;
import com.example.springmvcrest.api.v1.model.VendorDTO;
import com.example.springmvcrest.controllers.v1.VendorController;
import com.example.springmvcrest.domain.Vendor;
import com.example.springmvcrest.repositories.VendorRepository;
import junit.framework.TestCase;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceTest extends TestCase {


    public static final long ID = 1L;
    public static final String NAME = "Joe";
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    public void testGetAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(2, vendorDTOS.size());
    }

    public void testGetVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }

    public void testCreateNewVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL+ ID, savedDto.getVendorUrl());
    }

    public void testSaveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDto = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDto.getName());
        assertEquals(VendorController.BASE_URL+ ID, savedDto.getVendorUrl());
    }

    public void testPatchVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = new Vendor();
        vendor.setName("Smith");
        vendor.setId(ID);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when

        VendorDTO savedVendorDTO = vendorService.patchVendor(ID, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(savedVendorDTO.getVendorUrl(), containsString("1"));
    }

    public void testDeleteVendorById() {
        //when
        vendorService.deleteVendorById(1L);

        //then
        then(vendorRepository).should().deleteById(anyLong());
    }
}