package com.mygy.gojimapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingProgramm implements Serializable {
    private String name = "Супер программа";
    private String description = "Супер программа";
    private List<TrainingDay> trainingDays = new ArrayList<>();
    private static final ArrayList<TrainingProgramm> allPrograms=new ArrayList<>();

    public TrainingProgramm(String name) {
        if(name != null)
            this.name = name;
        allPrograms.add(this);
    }

    public TrainingProgramm(String name, TrainingDay... trainingDays) {
        if(name != null)
              this.name = name;
        this.trainingDays = Arrays.asList(trainingDays);
        allPrograms.add(this);
    }

    public String getName() {
        return name;
    }
    public List<TrainingDay> getTrainingDays() {
        return trainingDays;
    }
    public static ArrayList<TrainingProgramm> getAllPrograms(){
        return allPrograms;
    }
    public static TrainingProgramm getTrainingProgramByName(String name){
        for(TrainingProgramm tp:allPrograms){
            if(tp.getName().equals(name)) return tp;
        }

        return null;
    }

    public void addTrainingDay(TrainingDay trainingDay){
        trainingDays.add(trainingDay);
    }
}
