package com.hometopia.coreservice.entity;

import com.hometopia.commons.persistence.BaseEntity;
import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.embedded.Vendor;
import com.hometopia.coreservice.entity.enumeration.ScheduleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "\"end\"")
    private LocalDateTime end;

    @Column(name = "vendor", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private Vendor vendor;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "documents", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    @Builder.Default
    private ArrayList<File> documents = new ArrayList<>();

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
}
