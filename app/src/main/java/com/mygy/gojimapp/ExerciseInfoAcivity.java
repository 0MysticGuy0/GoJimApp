package com.mygy.gojimapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mygy.gojimapp.adapters.ProgressGraphRecyclerAdapter;
import com.mygy.gojimapp.data.Exercise;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseInfoAcivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info_acivity);

        ImageButton backBtn = findViewById(R.id.exerciseInfo_backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        Exercise exercise;
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            exercise = (Exercise) arguments.getSerializable(Exercise.class.getSimpleName());

            TextView name = findViewById(R.id.exerciseInfo_name);
            name.setText(exercise.getName());

            LinearLayout extraInfoLL = findViewById(R.id.exerciseInfo_extraInfoLL);

            View tech = getLayoutInflater().inflate(R.layout.exercise_technique_tab,null);
            ((GifImageView)tech.findViewById(R.id.exerciseTechnique_gif)).setImageResource(exercise.getTechniqueRes());
            extraInfoLL.addView(tech);

            View tab = getLayoutInflater().inflate(R.layout.exercise_parameters_table,null);
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
                ((TextView) tab.findViewById(R.id.exerciseParametersTable_lastWeight))
                        .setText(Double.toString(lastParameters.get(0)));
                ((TextView) tab.findViewById(R.id.exerciseParametersTable_lastReps))
                        .setText(Double.toString(lastParameters.get(1)));
                ((TextView) tab.findViewById(R.id.exerciseParametersTable_lastSets))
                        .setText(Integer.toString((int) (double) (lastParameters.get(2))));
                extraInfoLL.addView(tab);

            RecyclerView graphsAdapter = findViewById(R.id.exerciseInfo_graphsRecycler);
            ProgressGraphRecyclerAdapter adapter = new ProgressGraphRecyclerAdapter(this,exercise.getProgressParameters());
            graphsAdapter.setAdapter(adapter);
        }
    }
}