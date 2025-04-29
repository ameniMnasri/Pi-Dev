package com.example.Activity.service.Activity;


import com.example.Activity.Repository.Activity.ActivityTypeRepository;
import com.example.Activity.entity.Activity.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeService {

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    // Create new ActivityType
    public ActivityType createActivityType(ActivityType activityType) {
        return activityTypeRepository.save(activityType);
    }

    // Get all ActivityTypes
    public List<ActivityType> getAllActivityTypes() {
        return activityTypeRepository.findAll();
    }

    // Get ActivityType by ID
    public Optional<ActivityType> getActivityTypeById(Long id) {
        return activityTypeRepository.findById(id);
    }

    // Update ActivityType
    public ActivityType updateActivityType(Long id, ActivityType updatedActivityType) {
        return activityTypeRepository.findById(id)
                .map(activityType -> {
                    activityType.setTitle(updatedActivityType.getTitle());
                    activityType.setDescription(updatedActivityType.getDescription());
                    activityType.setType(updatedActivityType.getType());
                    activityType.setBodyPart(updatedActivityType.getBodyPart());
                    activityType.setEquipment(updatedActivityType.getEquipment());
                    activityType.setLevel(updatedActivityType.getLevel());
                    activityType.setRating(updatedActivityType.getRating());
                    activityType.setRatingDesc(updatedActivityType.getRatingDesc());
                    return activityTypeRepository.save(activityType);
                })
                .orElseThrow(() -> new RuntimeException("ActivityType not found with id: " + id));
    }

    // Delete ActivityType
    public void deleteActivityType(Long id) {
        activityTypeRepository.deleteById(id);
    }
}

