package com.hometopia.coreservice.service.impl;

import com.hometopia.commons.exception.ResourceNotFoundException;
import com.hometopia.commons.response.ListResponse;
import com.hometopia.coreservice.dto.response.DistrictResponse;
import com.hometopia.coreservice.entity.District;
import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.Province;
import com.hometopia.coreservice.entity.embedded.DistrictLanId;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.mapper.DistrictMapper;
import com.hometopia.coreservice.repository.DistrictLanRepository;
import com.hometopia.coreservice.service.DistrictService;
import com.hometopia.proto.district.GetDistrictRequest;
import com.hometopia.proto.district.GetDistrictResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictMapper districtMapper;
    private final DistrictLanRepository districtLanRepository;

    @Override
    public ListResponse<DistrictResponse> getListDistricts(CountryCode code) {
        return ListResponse.of(districtLanRepository.findAllByIdCountryCode(code).stream()
                .map(districtMapper::toDistrictResponse)
                .toList());
    }

    @Override
    public ListResponse<DistrictResponse> getListDistricts(Integer provinceId, CountryCode code) {
        Province province = new Province();
        province.setCode(provinceId);
        District district = new District();
        district.setProvince(province);
        DistrictLan districtLan = new DistrictLan();
        DistrictLanId id = new DistrictLanId();
        id.setCountryCode(code);
        districtLan.setId(id);
        districtLan.setDistrict(district);

        return ListResponse.of(districtLanRepository.findAll(Example.of(districtLan)).stream()
                .map(districtMapper::toDistrictResponse)
                .toList());
    }

    @Override
    public GetDistrictResponse getGetDistrict(GetDistrictRequest request) {
        CountryCode countryCode = CountryCode.valueOf(request.getCountryCode());
        return Optional.ofNullable(districtLanRepository.findByNameAndProvinceIdAndCountryCode(request.getName(), request.getProvinceCode(), countryCode))
                .map(districtLan -> GetDistrictResponse.newBuilder()
                        .setCode(districtLan.getDistrict().getCode())
                        .setName(districtLan.getName())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("DistrictLan", "name", request.getName()));
    }
}
