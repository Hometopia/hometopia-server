package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.ProvinceResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.mapper.ProvinceMapper;
import com.hometopia.coreservice.repository.ProvinceLanRepository;
import com.hometopia.coreservice.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceMapper provinceMapper;
    private final ProvinceLanRepository provinceLanRepository;

    @Override
    public ListResponse<ProvinceResponse> getListProvinces(CountryCode code) {
        return ListResponse.of(provinceLanRepository.findAllByIdCountryCode(code).stream()
                .map(provinceMapper::toProvinceResponse)
                .toList());
    }
}
