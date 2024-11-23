package com.hometopia.coreservice.controller;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.DistrictResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<DistrictResponse>> getListDistricts(@RequestParam Optional<Integer> provinceId,
                                                                           @RequestParam Optional<CountryCode> code) {
        return provinceId.map(id -> ResponseEntity.ok(districtService.getListDistricts(provinceId.get(), code.orElse(CountryCode.VN))))
                .orElseGet(() -> ResponseEntity.ok(districtService.getListDistricts(code.orElse(CountryCode.VN))));
    }
}
