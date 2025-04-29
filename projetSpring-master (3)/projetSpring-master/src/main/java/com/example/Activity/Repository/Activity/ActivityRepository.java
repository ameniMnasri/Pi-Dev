package com.example.Activity.Repository.Activity;

import com.example.Activity.entity.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByUserId(Long userId);

}
