package com.example.springmvcrest.api.v1.model;

import com.example.springmvcrest.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {
    List<Vendor> vendors;
}
