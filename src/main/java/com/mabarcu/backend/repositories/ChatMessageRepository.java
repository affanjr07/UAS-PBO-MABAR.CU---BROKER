package com.mabarcu.backend.repositories;

import com.mabarcu.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    List<ChatMessage> findByChannelIgnoreCaseOrderByIdAsc(String channel);

    List<ChatMessage> findBySenderContainingIgnoreCase(String sender);
}