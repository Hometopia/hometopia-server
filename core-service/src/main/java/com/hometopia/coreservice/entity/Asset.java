package com.hometopia.coreservice.entity;

import com.hometopia.commons.enumeration.AssetCategory;
import com.hometopia.commons.persistence.BaseEntity;
import com.hometopia.coreservice.entity.embedded.File;
import com.hometopia.coreservice.entity.enumeration.AssetStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "asset")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "images", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    @Builder.Default
    private ArrayList<File> images = new ArrayList<>();

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "purchase_place")
    private String purchasePlace;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "brand")
    private String brand;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "warranty_expiry_date")
    private LocalDate warrantyExpiryDate;

    @Column(name = "documents", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    @Builder.Default
    private ArrayList<File> documents = new ArrayList<>();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Column(name = "maintenance_cycle")
    private Integer maintenanceCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "label")
    @Enumerated(EnumType.STRING)
    private AssetCategory label;

    @Column(name = "useful_life")
    private Integer usefulLife;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<AssetLifeCycle> assetLifeCycles = new HashSet<>();

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Schedule> schedules = new HashSet<>();
}
