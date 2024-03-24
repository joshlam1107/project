package com.fsse2401.project.repository;

import com.fsse2401.project.entity.TransactionProductEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TransactionProductRepository extends CrudRepository<TransactionProductEntity, Integer> {
    List<TransactionProductEntity> getTransactionProductEntitiesByTransactionInfoTid (Integer tid);
}