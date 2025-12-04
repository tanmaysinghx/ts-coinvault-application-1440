package com.ts.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions") // Plural is standard for table names
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User receiver;

    public Transaction() {}

    public Transaction(User sender, User receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
}