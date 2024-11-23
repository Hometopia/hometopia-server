package com.hometopia.coreservice.entity;

import com.hometopia.commons.persistence.AuditEntity;
import com.hometopia.coreservice.entity.embedded.WardLanId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

@Entity
@Table(name = "ward_lan")
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardLan extends AuditEntity {
    @EmbeddedId
    private WardLanId id;

    @MapsId("wardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @Column(name = "name", nullable = false)
    private String name;
}
