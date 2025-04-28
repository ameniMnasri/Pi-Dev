package com.example.Activity.entity;

import java.util.List;

public class ExerciseSuggestion {
    private List<String> warmup;
    private List<String> workout;
    private List<String> cooldown;
    private String advice;

    public ExerciseSuggestion() {
    }

    public ExerciseSuggestion(List<String> warmup, List<String> workout, List<String> cooldown, String advice) {
        this.warmup = warmup;
        this.workout = workout;
        this.cooldown = cooldown;
        this.advice = advice;
    }

    public List<String> getWarmup() {
        return warmup;
    }

    public void setWarmup(List<String> warmup) {
        this.warmup = warmup;
    }

    public List<String> getWorkout() {
        return workout;
    }

    public void setWorkout(List<String> workout) {
        this.workout = workout;
    }

    public List<String> getCooldown() {
        return cooldown;
    }

    public void setCooldown(List<String> cooldown) {
        this.cooldown = cooldown;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
