package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProvinceRepository extends JpaRepository<Province, Integer>, QuerydslPredicateExecutor<Province>,
        JpaSpecificationExecutor<Province> {
}
