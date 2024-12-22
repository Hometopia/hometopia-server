package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.ProvinceResponse;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.coreservice.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<ProvinceResponse>> getListProvinces(@RequestParam Optional<CountryCode> code) {
        return ResponseEntity.ok(provinceService.getListProvinces(code.orElse(CountryCode.VN)));
    }
}
