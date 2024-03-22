package com.fsse2401.project.repository;

import com.fsse2401.project.entity.TransactionEntity;
import com.fsse2401.project.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Integer> {
    Optional<TransactionEntity> findTransactionEntityByTidAndUserFirebaseUid (Integer tid, String firebaseUid);
}
