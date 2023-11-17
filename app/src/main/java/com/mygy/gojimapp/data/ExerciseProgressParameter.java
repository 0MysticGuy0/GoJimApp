package com.mygy.gojimapp.data;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExerciseProgressParameter extends ProgressParameter{
    private static ParameterInfo[] exerciseParametersInfo = new ParameterInfo[]{
            new ProgressParameter.ParameterInfo("вес","кг"),
            new ProgressParameter.ParameterInfo("повторения","раз"),
            new ProgressParameter.ParameterInfo("подходы","раз") };

    public ExerciseProgressParameter(String exerciseName) {
        super(exerciseName, Group.EXERCISES,exerciseParametersInfo );
    }
    public static ExerciseProgressParameter getExerciseProgressParameterFromDoc(HashMap<String, Object> doc){
        ExerciseProgressParameter res;
        String name = (String)doc.get("name");

        res = new ExerciseProgressParameter(name);

        try {
            List<HashMap<String, Object>> params = (List<HashMap<String, Object>>) doc.get("parameters");
            for (HashMap<String, Object> p : params) {
                res.addParameter(new ExerciseParameter(p));
            }
        }catch (NullPointerException ex){
            return null;
        }
        return res;
    }

    public ExerciseParameter addParameter(ExerciseParameter parameter) {
        return (ExerciseParameter) super.addParameter(parameter);
    }

    public static class ExerciseParameter extends Parameter{
        public ExerciseParameter(Date date, double weight, double reps,double sets) {
            super(date, weight,reps,sets);
        }
        public ExerciseParameter(HashMap<String,Object> doc){
            super(doc);
        }
        public double getWeight(){
            return values.get(0);
        }
        public double getReps(){
            return values.get(1);
        }
        public double getSets(){
            return values.get(2);
        }
    }

}
