package com.hometopia.coreservice.controller.rest;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.WardResponse;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.coreservice.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/wards")
@RequiredArgsConstructor
public class WardController {

    private final WardService wardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListResponse<WardResponse>> getListWards(@RequestParam Optional<CountryCode> countryCode,
                                                            @RequestParam Optional<Integer> districtId) {
        return districtId.map(integer -> ResponseEntity.ok(wardService.getListWards(integer, countryCode.orElse(CountryCode.VN))))
                .orElseGet(() -> ResponseEntity.ok(wardService.getListWards(countryCode.orElse(CountryCode.VN))));
    }
}
