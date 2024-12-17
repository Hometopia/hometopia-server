package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.WardResponse;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.mapper.WardMapper;
import com.hometopia.coreservice.repository.WardLanRepository;
import com.hometopia.coreservice.service.WardService;
import com.hometopia.proto.ward.GetWardRequest;
import com.hometopia.proto.ward.GetWardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardMapper wardMapper;
    private final WardLanRepository wardLanRepository;

    @Override
    public ListResponse<WardResponse> getListWards(CountryCode countryCode) {
        return ListResponse.of(wardLanRepository.findAllByIdCountryCode(countryCode).stream()
                .map(wardMapper::toWardResponse)
                .toList());
    }

    @Override
    public ListResponse<WardResponse> getListWards(Integer districtId, CountryCode countryCode) {
        return ListResponse.of(wardLanRepository.findAllByDistrictIdAndCountryCode(districtId, countryCode).stream()
                .map(wardMapper::toWardResponse)
                .toList());
    }

    @Override
    public GetWardResponse getWard(GetWardRequest request) {
        CountryCode countryCode = CountryCode.valueOf(request.getCountryCode());
        return wardLanRepository.findOneByNameContainingIgnoreCaseAndIdCountryCode(request.getName(), countryCode)
                .map(wardLan -> GetWardResponse.newBuilder()
                        .setCode(wardLan.getWard().getCode())
                        .setName(wardLan.getName())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("WardLan", "name", request.getName()));
    }
}
