package com.example.Activity.controller;

import com.example.Activity.entity.ExerciseSuggestion;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*") // Allow Angular to call without issues
public class ExerciseController {

    private final Random random = new Random();

    private final List<String> warmupExercises = Arrays.asList(
            "Jumping Jacks", "High Knees", "Jump Rope", "Arm Circles", "Side Shuffles"
    );

    private final List<String> workoutWeightLoss = Arrays.asList(
            "Squats", "Push-ups", "Burpees", "Lunges", "Mountain climbers", "Dynamic Plank"
    );

    private final List<String> workoutMuscleGain = Arrays.asList(
            "Weighted Squats", "Diamond Push-ups", "Pull-ups", "Shoulder Press", "Deadlifts", "Chair Dips"
    );

    private final List<String> workoutFitness = Arrays.asList(
            "Crunches", "Plank", "Side Lunges", "Superman", "Step-ups", "Inverted Rows"
    );

    private final List<String> cooldownExercises = Arrays.asList(
            "Quadriceps Stretch", "Arm Stretch", "Back Stretch", "Calf Stretch", "Deep Breathing"
    );

    @PostMapping("/generate")
    public ExerciseSuggestion generateExercise(@RequestParam String goal,
                                               @RequestParam String level,
                                               @RequestParam int timeMinutes) {

        List<String> warmup = pickRandomExercises(warmupExercises, 2);
        List<String> workout = pickWorkoutExercises(goal, 4);
        List<String> cooldown = pickRandomExercises(cooldownExercises, 2);

        String advice = generateAdvice(goal, level);

        return new ExerciseSuggestion(warmup, workout, cooldown, advice);
    }

    private List<String> pickRandomExercises(List<String> exercises, int count) {
        List<String> shuffled = new ArrayList<>(exercises);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    private List<String> pickWorkoutExercises(String goal, int count) {
        List<String> selected;

        switch (goal.toLowerCase()) {
            case "weight loss":
                selected = workoutWeightLoss;
                break;
            case "muscle gain":
                selected = workoutMuscleGain;
                break;
            case "fitness":
            default:
                selected = workoutFitness;
                break;
        }

        return pickRandomExercises(selected, count);
    }

    private String generateAdvice(String goal, String level) {
        String baseAdvice = "";

        switch (goal.toLowerCase()) {
            case "weight loss":
                baseAdvice = "Maintain high intensity and limit rest periods.";
                break;
            case "muscle gain":
                baseAdvice = "Focus on movement quality with appropriate weights.";
                break;
            case "fitness":
                baseAdvice = "Work progressively to avoid injuries.";
                break;
        }

        if (level.equalsIgnoreCase("beginner")) {
            baseAdvice += " Take more breaks if needed.";
        } else if (level.equalsIgnoreCase("advanced")) {
            baseAdvice += " Add more challenging variations if possible.";
        }

        return baseAdvice;
    }
}