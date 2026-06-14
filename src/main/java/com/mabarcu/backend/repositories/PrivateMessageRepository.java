package com.mabarcu.backend.repositories;

import com.mabarcu.models.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {

    @Query("SELECT p FROM PrivateMessage p WHERE (p.senderId = :user1 AND p.receiverId = :user2) OR (p.senderId = :user2 AND p.receiverId = :user1) ORDER BY p.id ASC")
    List<PrivateMessage> findConversation(@Param("user1") int user1, @Param("user2") int user2);
}
