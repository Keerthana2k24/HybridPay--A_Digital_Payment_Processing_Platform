package com.hybridpay.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private String receiverId;
    private Double amount;
    private String type;
    private String status;
    private String note;
    private String mode;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
