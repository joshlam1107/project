package com.fsse2401.project.repository;

import com.fsse2401.project.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findProductEntityByPid (Integer pid);
}
