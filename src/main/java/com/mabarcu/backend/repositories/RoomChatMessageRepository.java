package com.mabarcu.backend.repositories;

import com.mabarcu.models.RoomChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomChatMessageRepository extends JpaRepository<RoomChatMessage, Integer> {

    List<RoomChatMessage> findByRoomIdOrderByIdAsc(int roomId);

    void deleteByRoomId(int roomId);
}