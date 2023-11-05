package com.mygy.gojimapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mygy.gojimapp.mainPage.ChroniclesOfProgressFragment;
import com.mygy.gojimapp.mainPage.FavouriteProgramsFragment;

public class HomeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button favPrograms = view.findViewById(R.id.homePage_trainBtn);
        favPrograms.setOnClickListener(v -> {
            replaceFragment(new FavouriteProgramsFragment());
        });

        Button progress = view.findViewById(R.id.homePage_progrressBtn);
        progress.setOnClickListener(v -> {
            replaceFragment(new ChroniclesOfProgressFragment());
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