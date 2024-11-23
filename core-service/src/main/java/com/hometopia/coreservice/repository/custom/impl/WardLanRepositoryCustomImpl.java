package com.hometopia.coreservice.repository.custom.impl;

import com.hometopia.coreservice.entity.QWardLan;
import com.hometopia.coreservice.entity.WardLan;
import com.hometopia.coreservice.entity.enumeration.CountryCode;
import com.hometopia.coreservice.repository.custom.WardLanRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WardLanRepositoryCustomImpl implements WardLanRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WardLan> findAllByDistrictIdAndCountryCode(Integer districtId, CountryCode code) {
        return queryFactory.selectFrom(QWardLan.wardLan)
                .join(QWardLan.wardLan.ward)
                .where(QWardLan.wardLan.ward.district.code.eq(districtId)
                        .and(QWardLan.wardLan.id.countryCode.eq(code)))
                .fetch();
    }
}
