package com.mygy.gojimapp.data;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.ExerciseRecyclerAdapter;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class Exercise implements Serializable {
    private final String name;
    private final String description = "Описание";
    private final int techniqueRes;
    private final String reccomendedSets,reccomendedReps;
    private transient ExtraTab openedExtraTab = ExtraTab.NONE;
    private final MuscleGroup[] usedMuscleGroups;
    private ExerciseProgressParameter progressParameters = null;
    public static final ArrayList<Exercise> allExercises = new ArrayList<>();
    public static final ArrayList<Exercise> allBodyExercises = new ArrayList<>();
    public static final ArrayList<Exercise> allArmsExercises = new ArrayList<>();
    public static final ArrayList<Exercise> allLegsExercises = new ArrayList<>();


    public Exercise(String name, String reccomendedSets, String reccomendedReps,int techniqueRes, MuscleGroup[] usedMuscleGroups) {
        this.name = name;
        this.reccomendedSets = reccomendedSets;
        this.reccomendedReps = reccomendedReps;
        this.techniqueRes = techniqueRes;
        this.usedMuscleGroups = usedMuscleGroups;
        allExercises.add(this);

        for(MuscleGroup gr:this.usedMuscleGroups){
            switch(gr){
                case BODY: allBodyExercises.add(this); break;
                case ARMS: allArmsExercises.add(this); break;
                case LEGS: allLegsExercises.add(this); break;
                default:
            }
        }

    }


    public ExerciseProgressParameter getProgressParameters() {
        if(progressParameters == null) {
            progressParameters = ExerciseProgressParameter.getExercisesParameterByName(name);

            //System.out.println("ppp\n"+ProgressParameter.allExercisesParameters+"\nppp");
            if(progressParameters == null){
                System.out.println("не гружу упр парам");
                progressParameters = new ExerciseProgressParameter(name);
            }
        }
        return progressParameters;
    }
    public ArrayList<ProgressParameter.Parameter> getProgressParametersList() {
        return progressParameters.getParameters();
    }

    public String getName() {
        return name;
    }

    public int getTechniqueRes() {
        return techniqueRes;
    }

    public String getDescription() {
        return description;
    }

    public ExtraTab getOpenedExtraTab() {
        return openedExtraTab;
    }

    public void setOpenedExtraTab(ExtraTab openedExtraTab) {
        this.openedExtraTab = openedExtraTab;
    }

    public String getReccomendedSets() {
        return reccomendedSets;
    }

    public String getReccomendedReps() {
        return reccomendedReps;
    }

    public static Exercise getExerciseByName(String name){
        for(Exercise exercise:allExercises){
            if(exercise.getName().equals(name)) return exercise;
        }
        return null;
    }

    public static enum ExtraTab{
        PARAMETERS,
        TECHNIQUE,
        NONE
    }
    public static enum MuscleGroup implements Serializable{
        BODY,
        ARMS,
        LEGS
    }
    public static enum Muscles{
        CHEST,
        ABS,
        BACK,
        NECK,
        TRAPEZIUS,
        LOWER_BACK,//поясница

        SHOULDERS,
        BICEPS,
        TRICEPS,
        FOREARM, //предплечья

        HIP,//бедра
        CALF, //икры
        GLUTEUS //ягодияные мышцы
    }
}
