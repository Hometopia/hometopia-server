package com.hometopia.coreservice.repository.custom.impl;

import com.hometopia.coreservice.entity.DistrictLan;
import com.hometopia.coreservice.entity.QDistrict;
import com.hometopia.coreservice.entity.QDistrictLan;
import com.hometopia.coreservice.entity.QProvince;
import com.hometopia.commons.enumeration.CountryCode;
import com.hometopia.coreservice.repository.custom.DistrictLanRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistrictLanRepositoryCustomImpl implements DistrictLanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public DistrictLan findByNameAndProvinceIdAndCountryCode(String name, Integer provinceId, CountryCode countryCode) {
        return queryFactory.selectFrom(QDistrictLan.districtLan)
                .join(QDistrictLan.districtLan.district, QDistrict.district)
                .join(QDistrict.district.province, QProvince.province)
                .where(QDistrictLan.districtLan.name.containsIgnoreCase(name).and(QProvince.province.code.eq(provinceId))
                        .and(QDistrictLan.districtLan.id.countryCode.eq(countryCode)))
                .fetchOne();
    }
}
