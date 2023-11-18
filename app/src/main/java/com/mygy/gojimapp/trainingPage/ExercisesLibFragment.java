package com.mygy.gojimapp.trainingPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.TrainingDayRecyclerAdapter;
import com.mygy.gojimapp.data.Exercise;
import com.mygy.gojimapp.adapters.ExerciseRecyclerAdapter;
import com.mygy.gojimapp.data.Store;
import com.mygy.gojimapp.data.TrainingDay;

import java.util.ArrayList;


public class ExercisesLibFragment extends Fragment {
    private TrainingDayRecyclerAdapter adapter;
    private static TrainingDay bodyEx = new TrainingDay("Тело", Exercise.allBodyExercises);
    private static TrainingDay armsEx = new TrainingDay("Руки", Exercise.allArmsExercises);
    private static TrainingDay legsEx = new TrainingDay("Ноги", Exercise.allLegsExercises);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises_lib, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.exercisesLib_recycler);

        ArrayList<TrainingDay> muscleGroups = new ArrayList<>();
        muscleGroups.add(bodyEx);
        muscleGroups.add(armsEx);
        muscleGroups.add(legsEx);
        adapter = new TrainingDayRecyclerAdapter(getActivity(), muscleGroups,false);
        recyclerView.setAdapter(adapter);

        EditText searchTxt = view.findViewById(R.id.exercisesLib_searchText);
        ImageButton searchBtn = view.findViewById(R.id.exercisesLib_searchBtn);
        searchBtn.setOnClickListener(v -> {
            String txt = (searchTxt.getText().toString()).toLowerCase();
            if(txt.length() > 0){
                ArrayList<Exercise> bodySearch = new ArrayList<>();
                for(Exercise ex:Exercise.allBodyExercises){
                    if(ex.getName().toLowerCase().contains(txt)){
                        bodySearch.add(ex);
                    }
                }
                ArrayList<Exercise> armsSearch = new ArrayList<>();
                for(Exercise ex:Exercise.allArmsExercises){
                    if(ex.getName().toLowerCase().contains(txt)){
                        armsSearch.add(ex);
                    }
                }
                ArrayList<Exercise> legsSearch = new ArrayList<>();
                for(Exercise ex:Exercise.allLegsExercises){
                    if(ex.getName().toLowerCase().contains(txt)){
                        legsSearch.add(ex);
                    }
                }

                bodyEx.setExercisesList(bodySearch);
                armsEx.setExercisesList(armsSearch);
                legsEx.setExercisesList(legsSearch);

            }else{
                bodyEx.setExercisesList(Exercise.allBodyExercises);
                armsEx.setExercisesList(Exercise.allArmsExercises);
                legsEx.setExercisesList(Exercise.allLegsExercises);
            }
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.closeLastOpenedExtraTab();
    }
}