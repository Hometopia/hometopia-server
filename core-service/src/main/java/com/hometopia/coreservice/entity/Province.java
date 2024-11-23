package com.hometopia.coreservice.entity;

import com.hometopia.commons.persistence.AuditEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "province")
@Immutable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Province extends AuditEntity {
    @Id
    @Column(name = "code")
    private Integer code;

    @Builder.Default
    @OneToMany(mappedBy = "province")
    private Set<ProvinceLan> languages = new HashSet<>();
}
