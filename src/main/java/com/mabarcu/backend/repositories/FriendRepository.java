package com.mabarcu.backend.repositories;

import com.mabarcu.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface FriendRepository extends JpaRepository<Friend, Integer> {
    List<Friend> findByUserId(int userId);
    void deleteByUserIdAndFriendNameContainingIgnoreCase(int userId,String friendName);
}
