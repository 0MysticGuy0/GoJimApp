package com.mygy.gojimapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingDay implements Serializable {
    private String name;
    private List<Exercise> exercises = new ArrayList<>();

    public TrainingDay(String name) {
        this.name = name;
    }

    public TrainingDay(String name, Exercise...exercises) {
        this.name = name;
        this.exercises = Arrays.asList(exercises);
    }
    public TrainingDay(String name,List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public void setExercisesList(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
