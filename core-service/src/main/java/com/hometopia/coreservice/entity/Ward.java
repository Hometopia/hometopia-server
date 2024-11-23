package com.hometopia.coreservice.entity;

import com.hometopia.commons.persistence.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ward")
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ward extends AuditEntity {
    @Id
    @Column(name = "code")
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @Builder.Default
    @OneToMany(mappedBy = "ward")
    private Set<WardLan> languages = new HashSet<>();
}
