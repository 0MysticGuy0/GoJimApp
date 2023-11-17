package com.mygy.gojimapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mygy.gojimapp.ExerciseInfoAcivity;
import com.mygy.gojimapp.MainActivity;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.TrainingProgramActivity;
import com.mygy.gojimapp.data.Exercise;
import com.mygy.gojimapp.data.ProgressParameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private  List<Exercise> exercises;
    private LinearLayout lastOpenedExtraTab;
    private Exercise lastOpenedExtraTabExercise;

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public ExerciseRecyclerAdapter(Context context, List<Exercise> exercises) {
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExerciseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.exercise_button_lib, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseRecyclerAdapter.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.exerciseBtn.setText((position+1)+". "+exercise.getName());

        holder.exerciseParameters.setOnClickListener(v -> {
            changeExtraTab(exercise, Exercise.ExtraTab.PARAMETERS,holder);
        });

        holder.techniqueBtn.setOnClickListener(v -> {
            changeExtraTab(exercise, Exercise.ExtraTab.TECHNIQUE,holder);
        });

        holder.addParametersBn.setOnClickListener(v -> {
            System.out.println(inflater.getContext().getClass().getName());
            if(inflater.getContext() instanceof FragmentActivity) {
                ProgressParameter.createSaveParameterPopup(exercise.getProgressParameters(), (FragmentActivity)inflater.getContext());
            }
            closeLastOpenedExtraTab();
        });

        holder.exerciseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(inflater.getContext(), ExerciseInfoAcivity.class);
            intent.putExtra(Exercise.class.getSimpleName(), exercise);
            inflater.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }
    private void changeExtraTab(Exercise exercise, Exercise.ExtraTab newTab,ExerciseRecyclerAdapter.ViewHolder holder){
        holder.replaceExtraTab(exercise, newTab,inflater);
        if(lastOpenedExtraTabExercise!=exercise )
            closeLastOpenedExtraTab();
        lastOpenedExtraTab=holder.extraTab;
        lastOpenedExtraTabExercise=exercise;
    }

    public void closeLastOpenedExtraTab(){
        if(lastOpenedExtraTab!=null)
            lastOpenedExtraTab.removeAllViews();
        if(lastOpenedExtraTabExercise!=null)
            lastOpenedExtraTabExercise.setOpenedExtraTab(Exercise.ExtraTab.NONE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Button exerciseBtn;
        public final LinearLayout extraTab;
        public final ImageButton exerciseParameters, techniqueBtn, addParametersBn;

        ViewHolder(View view) {
            super(view);
            exerciseBtn = view.findViewById(R.id.exerciseButton_exerciseButton);
            extraTab = view.findViewById(R.id.exerciseButton_extraTab);
            exerciseParameters = view.findViewById(R.id.exerciseButton_showExerciseParametersBtn);
            techniqueBtn = view.findViewById(R.id.exerciseButton_showTechnicsBtn);
            addParametersBn = view.findViewById(R.id.exerciseButton_addParameters);
        }

        public void replaceExtraTab(Exercise exercise,Exercise.ExtraTab newTab, LayoutInflater inflater) {
            if(newTab.equals(exercise.getOpenedExtraTab())) {
                extraTab.removeAllViews();
                exercise.setOpenedExtraTab(Exercise.ExtraTab.NONE);
            }

            else {
                extraTab.removeAllViews();
                View tab;
                exercise.setOpenedExtraTab(newTab);
                switch(newTab){
                    case PARAMETERS:
                        tab = inflater.inflate(R.layout.exercise_parameters_table,null);
                        ((TextView)tab.findViewById(R.id.exerciseParametersTable_recommendedSets)).setText(exercise.getReccomendedSets());
                        ((TextView)tab.findViewById(R.id.exerciseParametersTable_recommendedReps)).setText(exercise.getReccomendedReps());

                        List<Double> lastParameters;
                        try {
                            lastParameters = exercise.getProgressParametersList()
                                    .get(exercise.getProgressParametersList().size() - 1).getValues();//список сохраненных в последний раз показателей упражнения(вес, повторения, подходы)
                        }catch (RuntimeException ex){
                            lastParameters = new ArrayList<>();
                            lastParameters.add(0.0);
                            lastParameters.add(0.0);
                            lastParameters.add(0.0);
                        }
                        ((TextView)tab.findViewById(R.id.exerciseParametersTable_lastWeight))
                                .setText(Double.toString(lastParameters.get(0)));
                        ((TextView)tab.findViewById(R.id.exerciseParametersTable_lastReps))
                                .setText(Double.toString(lastParameters.get(1)));
                        ((TextView)tab.findViewById(R.id.exerciseParametersTable_lastSets))
                                .setText(Integer.toString((int)(double)(lastParameters.get(2))));

                        break;
                    case TECHNIQUE:
                        tab = inflater.inflate(R.layout.exercise_technique_tab,null);
                        ((GifImageView)tab.findViewById(R.id.exerciseTechnique_gif)).setImageResource(exercise.getTechniqueRes());
                        break;
                    default:
                        tab = null;
                        exercise.setOpenedExtraTab(Exercise.ExtraTab.NONE);
                }
                if(tab!=null)
                    extraTab.addView(tab);
            }
        }

    }
}
