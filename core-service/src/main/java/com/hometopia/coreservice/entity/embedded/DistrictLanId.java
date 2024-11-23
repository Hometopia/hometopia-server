package com.hometopia.coreservice.entity.embedded;

import com.hometopia.coreservice.entity.enumeration.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DistrictLanId implements Serializable {
    private Integer districtId;

    @Column(name = "country_code", nullable = false, columnDefinition = "CHAR(2)")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
}
