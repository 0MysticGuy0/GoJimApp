package com.mygy.gojimapp.data;

import androidx.annotation.NonNull;

import com.mygy.gojimapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingProgramm implements Serializable {
    private final String name;
    private String description = "Супер программа";
    private final Difficulty difficulty;
    private int icoRes = R.drawable.zyzz;
    private List<TrainingDay> trainingDays = new ArrayList<>();
    private static final ArrayList<TrainingProgramm> allPrograms=new ArrayList<>();

    public TrainingProgramm(String name) {
        this.name = (name == null)? "none":name;
        difficulty = Difficulty.MEDIUM;
        allPrograms.add(this);
    }

    public TrainingProgramm(String name, Difficulty difficulty, TrainingDay... trainingDays) {
        this.name = (name == null)? "none" : name;
        this.difficulty = (difficulty == null)?Difficulty.MEDIUM : difficulty;
        this.trainingDays = Arrays.asList(trainingDays);
        allPrograms.add(this);
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setIcoRes(int icoRes) {
        this.icoRes = icoRes;
    }

    public int getIcoRes() {
        return icoRes;
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

    public static enum Difficulty implements Serializable{
        EASY,
        MEDIUM,
        HARD;

        @NonNull
        @Override
        public String toString() {
            switch (this){
                case EASY:
                    return "легкая";
                case MEDIUM:
                    return "средняя";
                case HARD:
                    return "сложная";
                default:
                    return "none";
            }
        }
    }
    public static enum Sex implements Serializable{
        MALE,
        FEMALE,
        BOTH;
    }
}
