package com.hybridpay.repository;

import com.hybridpay.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.senderId = :userId OR t.receiverId = CAST(:userId AS string)")
    List<Transaction> findAllByUser(Long userId);

    long countBySenderId(Long userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.senderId = :userId")
    Double sumSentByUser(Long userId);

    boolean existsByTypeAndNote(String type, String note);
}