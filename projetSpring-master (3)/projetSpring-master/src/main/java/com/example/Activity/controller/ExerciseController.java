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
@CrossOrigin(origins = "*") // autoriser Angular à appeler sans problème
public class ExerciseController {

    private final Random random = new Random();

    private final List<String> warmupExercises = Arrays.asList(
            "Jumping Jacks", "Montées de genoux", "Corde à sauter", "Cercles de bras", "Pas chassés"
    );

    private final List<String> workoutWeightLoss = Arrays.asList(
            "Squats", "Pompes", "Burpees", "Fentes", "Mountain climbers", "Planche dynamique"
    );

    private final List<String> workoutMuscleGain = Arrays.asList(
            "Squats lestés", "Pompes diamant", "Tractions", "Développé militaire", "Soulevé de terre", "Dips sur chaise"
    );

    private final List<String> workoutFitness = Arrays.asList(
            "Abdos crunch", "Gainage", "Fentes latérales", "Superman", "Step-ups", "Rowing inversé"
    );

    private final List<String> cooldownExercises = Arrays.asList(
            "Étirement des quadriceps", "Étirement des bras", "Étirement du dos", "Étirement des mollets", "Respiration profonde"
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
            case "perte de poids":
                selected = workoutWeightLoss;
                break;
            case "prise de masse":
                selected = workoutMuscleGain;
                break;
            case "remise en forme":
            default:
                selected = workoutFitness;
                break;
        }

        return pickRandomExercises(selected, count);
    }

    private String generateAdvice(String goal, String level) {
        String baseAdvice = "";

        switch (goal.toLowerCase()) {
            case "perte de poids":
                baseAdvice = "Maintenez une intensité élevée et limitez les temps de repos.";
                break;
            case "prise de masse":
                baseAdvice = "Concentrez-vous sur la qualité des mouvements avec des charges adaptées.";
                break;
            case "remise en forme":
                baseAdvice = "Travaillez progressivement pour éviter les blessures.";
                break;
        }

        if (level.equalsIgnoreCase("débutant")) {
            baseAdvice += " Prenez plus de pauses si nécessaire.";
        } else if (level.equalsIgnoreCase("avancé")) {
            baseAdvice += " Ajoutez des variations plus difficiles si possible.";
        }

        return baseAdvice;
    }
}
