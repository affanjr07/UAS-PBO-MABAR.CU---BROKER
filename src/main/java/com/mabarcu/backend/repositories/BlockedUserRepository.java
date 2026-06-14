package com.mabarcu.backend.repositories;

import com.mabarcu.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Integer> {
    List<BlockedUser> findByUserId(int userId);
}
