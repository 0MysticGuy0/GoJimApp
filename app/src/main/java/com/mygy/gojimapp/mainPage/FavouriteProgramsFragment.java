package com.mygy.gojimapp.mainPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mygy.gojimapp.MainActivity;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.TrainingProgramRecyclerAdapter;


public class FavouriteProgramsFragment extends Fragment {

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_programs, container, false);

        RecyclerView recycler = view.findViewById(R.id.favouritePrograms_recycler);
        TrainingProgramRecyclerAdapter adapter = new TrainingProgramRecyclerAdapter(getActivity(), MainActivity.user.getFavPrograms());
        recycler.setAdapter(adapter);

        return view;
    }
}