package com.example.Activity.Repository;

import com.example.Activity.entity.Activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
