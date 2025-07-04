package com.example.tgbot21.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ACTIVE_CHAT")
public class ActiveChat {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "CHAT_ID")
    private long chatId;
}
