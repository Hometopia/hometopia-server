package com.hometopia.vendorservice.controller;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.vendorservice.dto.response.GetListVendorResponse;
import com.hometopia.vendorservice.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<GetListVendorResponse>> getListVendors(@RequestParam AssetCategory category,
                                                                              @RequestParam Double lat,
                                                                              @RequestParam Double lon) {
        return ResponseEntity.ok(vendorService.getListVendors(category, lat, lon));
    }
}
