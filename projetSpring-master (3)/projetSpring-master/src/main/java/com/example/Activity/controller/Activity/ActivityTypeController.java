package com.example.Activity.controller.Activity;


import com.example.Activity.entity.Activity.ActivityType;
import com.example.Activity.service.Activity.ActivityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-types")  // << base URL
@CrossOrigin(origins = "*")  // Allow frontend access (you can restrict if needed)
public class ActivityTypeController {

    @Autowired
    private ActivityTypeService activityTypeService;

    // Create
    @PostMapping
    public ActivityType createActivityType(@RequestBody ActivityType activityType) {
        return activityTypeService.createActivityType(activityType);
    }

    // Get All
    @GetMapping
    public List<ActivityType> getAllActivityTypes() {
        return activityTypeService.getAllActivityTypes();
    }

    // Get By ID
    @GetMapping("/{id}")
    public ActivityType getActivityTypeById(@PathVariable Long id) {
        return activityTypeService.getActivityTypeById(id)
                .orElseThrow(() -> new RuntimeException("ActivityType not found with id: " + id));
    }

    // Update
    @PutMapping("/{id}")
    public ActivityType updateActivityType(@PathVariable Long id, @RequestBody ActivityType updatedActivityType) {
        return activityTypeService.updateActivityType(id, updatedActivityType);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void deleteActivityType(@PathVariable Long id) {
        activityTypeService.deleteActivityType(id);
    }
}
