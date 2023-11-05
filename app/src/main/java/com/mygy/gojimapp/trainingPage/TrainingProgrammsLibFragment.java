package com.mygy.gojimapp.trainingPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.TrainingProgramRecyclerAdapter;
import com.mygy.gojimapp.data.Store;
import com.mygy.gojimapp.data.TrainingProgramm;


public class TrainingProgrammsLibFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_programms_lib, container, false);

        Store.squat.getName();//чтобы вызвать статик-блок в сторадж // НУЖНО ИСПРАВИТЬ!!!
        RecyclerView recyclerView = view.findViewById(R.id.trainingPtogramsLib_recycler);
        TrainingProgramRecyclerAdapter adapter = new TrainingProgramRecyclerAdapter(getActivity(), TrainingProgramm.getAllPrograms());
        recyclerView.setAdapter(adapter);
        return view;
    }
}