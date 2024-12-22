package com.hometopia.coreservice.entity.embedded;

import com.hometopia.commons.enumeration.CountryCode;
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
public class WardLanId implements Serializable {
    private Integer wardId;

    @Column(name = "country_code", nullable = false, columnDefinition = "CHAR(2)")
    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
}
