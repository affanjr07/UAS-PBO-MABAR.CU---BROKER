package com.mabarcu.backend.repositories;

import com.mabarcu.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    List<FriendRequest> findByUserId(int userId);
    void deleteByUserIdAndRequesterContainingIgnoreCase(int userId,String requester);
}
