package com.mygy.gojimapp.data;

import java.util.ArrayList;

public class User {
    private String name="Супер Имя";
    private int age=18;
    private ArrayList<TrainingProgramm> favPrograms = new ArrayList<>();
    private ProgressParameter weightParameters = new ProgressParameter("вес", ProgressParameter.Group.BODY_PARAMETERS,new ProgressParameter.ParameterInfo("вес","кг"));
    private ProgressParameter heightParameters= new ProgressParameter("рост", ProgressParameter.Group.BODY_PARAMETERS,new ProgressParameter.ParameterInfo("рост","см"));

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<TrainingProgramm> getFavPrograms() {
        return favPrograms;
    }

    public void addFavProgram(TrainingProgramm program) {
        favPrograms.add(program);
    }
}
