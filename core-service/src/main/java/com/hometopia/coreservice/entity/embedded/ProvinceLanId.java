package com.hometopia.coreservice.entity.embedded;

import com.hometopia.coreservice.entity.enumeration.CountryCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProvinceLanId implements Serializable {
    private Integer provinceId;

    @Column(name = "country_code")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
}
