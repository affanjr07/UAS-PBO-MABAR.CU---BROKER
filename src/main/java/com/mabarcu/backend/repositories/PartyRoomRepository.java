package com.mabarcu.backend.repositories;

import com.mabarcu.models.PartyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRoomRepository extends JpaRepository<PartyRoom, Integer> {

    List<PartyRoom> findByGameContainingIgnoreCase(String game);

    List<PartyRoom> findByOwnerContainingIgnoreCase(String owner);

    List<PartyRoom> findByRoomTypeIgnoreCase(String roomType);
}