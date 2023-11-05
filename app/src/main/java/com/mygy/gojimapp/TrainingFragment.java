package com.mygy.gojimapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mygy.gojimapp.trainingPage.ExercisesLibFragment;
import com.mygy.gojimapp.trainingPage.TrainingProgrammsLibFragment;

public class TrainingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        Button exerciseLibBtn = view.findViewById(R.id.trainingPage_exercisesBtn);
        exerciseLibBtn.setOnClickListener(v->{
            replaceFragment(new ExercisesLibFragment());
        });

        Button trainingProgrammsBtn = view.findViewById(R.id.trainingPage_trainProgrBtn);
        trainingProgrammsBtn.setOnClickListener(v -> {
            replaceFragment(new TrainingProgrammsLibFragment());
        });

        return view;
    }

    private void replaceFragment(Fragment newFragment){
        try {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFragmentContainerView, newFragment);
            ft.commit();
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}