package com.example.springmvcrest.api.v1.mapper;

import com.example.springmvcrest.api.v1.model.VendorDTO;
import com.example.springmvcrest.domain.Vendor;
import junit.framework.TestCase;

public class VendorMapperTest extends TestCase {

    public static final String NAME = "Joe";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;
    public void testVendorToVendorDTO() {
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        assertEquals(NAME, vendorDTO.getName());
    }

    public void testVendorDtoToVendor() {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        assertEquals(NAME, vendor.getName());
    }
}