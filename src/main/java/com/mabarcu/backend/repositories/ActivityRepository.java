package com.mabarcu.backend.repositories;

import com.mabarcu.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByUserIdOrderByIdDesc(int userId);
}
