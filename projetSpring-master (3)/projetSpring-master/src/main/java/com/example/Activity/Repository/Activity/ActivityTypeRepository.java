package com.example.Activity.Repository.Activity;

import com.example.Activity.entity.Activity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType,Long> {
}
