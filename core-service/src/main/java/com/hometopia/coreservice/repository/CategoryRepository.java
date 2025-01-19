package com.hometopia.coreservice.repository;

import com.hometopia.coreservice.entity.Category;
import com.hometopia.coreservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    long countByUser(User user);
}
