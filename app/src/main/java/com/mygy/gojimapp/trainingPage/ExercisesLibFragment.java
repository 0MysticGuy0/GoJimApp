package com.mygy.gojimapp.trainingPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.TrainingDayRecyclerAdapter;
import com.mygy.gojimapp.data.Exercise;
import com.mygy.gojimapp.adapters.ExerciseRecyclerAdapter;
import com.mygy.gojimapp.data.Store;
import com.mygy.gojimapp.data.TrainingDay;

import java.util.ArrayList;


public class ExercisesLibFragment extends Fragment {
    private TrainingDayRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises_lib, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.exercisesLib_recycler);

        ArrayList<TrainingDay> muscleGroups = new ArrayList<>();
        muscleGroups.add(new TrainingDay("Тело", Exercise.allBodyExercises));
        muscleGroups.add(new TrainingDay("Руки", Exercise.allArmsExercises));
        muscleGroups.add(new TrainingDay("Ноги", Exercise.allLegsExercises));
        adapter = new TrainingDayRecyclerAdapter(getActivity(), muscleGroups,false);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        //adapter.closeLastOpenedExtraTab();
    }
}