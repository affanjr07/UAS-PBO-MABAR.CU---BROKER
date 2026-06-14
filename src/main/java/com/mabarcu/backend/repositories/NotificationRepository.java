package com.mabarcu.backend.repositories;

import com.mabarcu.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserIdOrderByIdDesc(int userId);
}
