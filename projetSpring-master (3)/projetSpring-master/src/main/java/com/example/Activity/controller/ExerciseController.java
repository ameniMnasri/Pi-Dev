package com.example.Activity.controller;

import com.example.Activity.entity.ExerciseSuggestion;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {

    // Exercise database organized by categories
    private enum ExerciseCategory {
        WARMUP, COOLDOWN, WEIGHT_LOSS, MUSCLE_GAIN, FITNESS
    }

    private final Map<ExerciseCategory, List<String>> exerciseDatabase = Map.of(
            ExerciseCategory.WARMUP, Arrays.asList(
                    "Jumping Jacks", "High Knees", "Jump Rope", "Arm Circles", "Side Shuffles",
                    "Butt Kicks", "Torso Twists", "Leg Swings", "Neck Rolls", "Ankle Rolls"
            ),
            ExerciseCategory.WEIGHT_LOSS, Arrays.asList(
                    "Squats", "Push-ups", "Burpees", "Lunges", "Mountain Climbers",
                    "Dynamic Plank", "Jump Squats", "Box Jumps", "Battle Ropes", "Kettlebell Swings"
            ),
            ExerciseCategory.MUSCLE_GAIN, Arrays.asList(
                    "Weighted Squats", "Diamond Push-ups", "Pull-ups", "Shoulder Press",
                    "Deadlifts", "Chair Dips", "Bench Press", "Bicep Curls", "Tricep Extensions", "Lat Pulldowns"
            ),
            ExerciseCategory.FITNESS, Arrays.asList(
                    "Crunches", "Plank", "Side Lunges", "Superman", "Step-ups",
                    "Inverted Rows", "Glute Bridges", "Bird Dogs", "Russian Twists", "Wall Sits"
            ),
            ExerciseCategory.COOLDOWN, Arrays.asList(
                    "Quadriceps Stretch", "Arm Stretch", "Back Stretch", "Calf Stretch",
                    "Deep Breathing", "Hamstring Stretch", "Hip Flexor Stretch", "Shoulder Stretch", "Child's Pose", "Cat-Cow"
            )
    );

    @PostMapping("/generate")
    public ExerciseSuggestion generateExercise(
            @RequestParam String goal,
            @RequestParam String level,
            @RequestParam int timeMinutes) throws IllegalArgumentException {

        // Input validation
        if (timeMinutes < 10 || timeMinutes > 120) {
            throw new IllegalArgumentException("Session duration should be between 10-120 minutes");
        }

        // Calculate exercise counts based on duration
        int warmupCount = Math.max(2, timeMinutes / 15);
        int workoutCount = Math.max(4, timeMinutes / 5);
        int cooldownCount = Math.max(2, timeMinutes / 30);

        // Get exercises
        List<String> warmup = getExercises(ExerciseCategory.WARMUP, warmupCount);
        List<String> workout = getWorkoutExercises(goal, workoutCount);
        List<String> cooldown = getExercises(ExerciseCategory.COOLDOWN, cooldownCount);

        // Generate personalized advice
        String advice = generateAdvice(goal, level, timeMinutes);

        return new ExerciseSuggestion(warmup, workout, cooldown, advice);
    }

    private List<String> getExercises(ExerciseCategory category, int count) {
        List<String> exercises = new ArrayList<>(exerciseDatabase.get(category));
        Collections.shuffle(exercises);
        return exercises.subList(0, Math.min(count, exercises.size()));
    }

    private List<String> getWorkoutExercises(String goal, int count) {
        ExerciseCategory category;
        switch (goal.toLowerCase()) {
            case "weight loss":
                category = ExerciseCategory.WEIGHT_LOSS;
                break;
            case "muscle gain":
                category = ExerciseCategory.MUSCLE_GAIN;
                break;
            case "fitness":
                category = ExerciseCategory.FITNESS;
                break;
            default:
                throw new IllegalArgumentException("Invalid goal specified");
        }
        return getExercises(category, count);
    }

    private String generateAdvice(String goal, String level, int duration) {
        StringBuilder advice = new StringBuilder();

        // Goal-specific advice
        switch (goal.toLowerCase()) {
            case "weight loss":
                advice.append("For optimal fat burning, maintain a heart rate between 60-80% of your maximum. ");
                advice.append("Circuit training with minimal rest (30-45s) between exercises works best. ");
                break;
            case "muscle gain":
                advice.append("Focus on progressive overload - aim for 3-4 sets of 8-12 reps per exercise. ");
                advice.append("Rest 60-90s between sets for optimal muscle recovery. ");
                break;
            case "fitness":
                advice.append("Mix strength and cardio exercises for balanced fitness. ");
                advice.append("Focus on proper form to prevent injuries. ");
                break;
        }

        // Level-specific advice
        switch (level.toLowerCase()) {
            case "beginner":
                advice.append("Start with 2-3 sessions per week. ");
                advice.append("Use modified versions of exercises if needed (e.g., knee push-ups). ");
                break;
            case "intermediate":
                advice.append("Aim for 3-5 sessions per week with varied intensity. ");
                advice.append("Consider adding weights or resistance bands to increase difficulty. ");
                break;
            case "advanced":
                advice.append("You can train 5-7 times per week with proper recovery. ");
                advice.append("Incorporate supersets and drop sets for increased intensity. ");
                break;
        }

        // Duration-specific advice
        if (duration > 60) {
            advice.append("For this long session, remember to stay hydrated and consider taking ");
            advice.append("short breaks (1-2 minutes) every 20-30 minutes.");
        }

        return advice.toString();
    }

    @GetMapping("/categories")
    public Map<String, List<String>> getExerciseCategories() {
        Map<String, List<String>> result = new HashMap<>();
        for (ExerciseCategory category : ExerciseCategory.values()) {
            result.put(category.name(), exerciseDatabase.get(category));
        }
        return result;
    }
}