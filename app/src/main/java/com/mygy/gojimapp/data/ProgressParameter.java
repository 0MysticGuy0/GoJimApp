package com.mygy.gojimapp.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//СДЕЛАТЬ класс-наследник для упражнений!!!
public class ProgressParameter implements Serializable {

    private ParameterInfo[] parametersInfo;//информация о параметрах(имя и единица измерения)
    private Group parameterGroup;
    private String name;
    private ArrayList<Parameter> parameters = new ArrayList<>();//сохраненные показатели
    public final static ArrayList<ProgressParameter> allExercisesParameters = new ArrayList<>();
    public final static ArrayList<ProgressParameter> allBodyParameters = new ArrayList<>();
    public final static ArrayList<ProgressParameter> allOtherParameters = new ArrayList<>();

    public ProgressParameter(String name, Group parameterGroup, ParameterInfo... parametersInfo) {
        this.name = name;
        this.parametersInfo = parametersInfo;
        this.parameterGroup = parameterGroup;

        switch(parameterGroup){
            case EXERCISES:
                allExercisesParameters.add(this);
                break;
            case BODY_PARAMETERS:
                allBodyParameters.add(this);
                break;
            case OTHER:
                allOtherParameters.add(this);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public Parameter addParameter(Parameter parameter){
        if(parameter.getDate() == null || parameter.getValues() == null || parameter.getValues().length != parametersInfo.length)
            return null;
        parameters.add(parameter);
        return parameter;
    }

    public static enum Group{
        EXERCISES,
        BODY_PARAMETERS,
        OTHER;

        @NonNull
        @Override
        public String toString() {
            String result="none";
            switch (this) {
                case EXERCISES: result = "упражнения";break;
                case BODY_PARAMETERS: result = "замеры тела";break;
                case OTHER: result = "другое";break;
            }
            return result;
        }
    }
    public static class Parameter implements Serializable{
        private double[] values;
        private Date date;

        public Parameter(Date date, double... values) {
            this.values = values;
            this.date = date;
        }

        public double[] getValues() {
            return values;
        }

        public Date getDate() {
            return date;
        }
    }
    public static class ParameterInfo implements Serializable{
        private final String parameterName;
        private final String parameterUnit;//единица измерения

        public ParameterInfo(String parameterName, String parameterUnit) {

            this.parameterName = parameterName;
            this.parameterUnit = parameterUnit;
        }
        public String getParameterName() {
            return parameterName;
        }

        public String getParameterUnit() {
            return parameterUnit;
        }
    }
}
