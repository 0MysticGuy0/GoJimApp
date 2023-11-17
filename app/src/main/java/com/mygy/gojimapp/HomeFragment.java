package com.mygy.gojimapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mygy.gojimapp.data.User;
import com.mygy.gojimapp.mainPage.ChroniclesOfProgressFragment;
import com.mygy.gojimapp.mainPage.FavouriteProgramsFragment;
import com.mygy.gojimapp.mainPage.SettingsActivity;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private User usr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        usr = MainActivity.user;

        TextView usrName = view.findViewById(R.id.homePage_userName);
        usrName.setText(usr.getName());

        TextView usrAge = view.findViewById(R.id.homePage_userAge);
        long age = TimeUnit.DAYS.convert( Math.abs(new Date().getTime() - usr.getBirthDate().getTime()), TimeUnit.MILLISECONDS)/365;
        usrAge.setText(Long.toString(age));

        ImageView ico = view.findViewById(R.id.homePage_userIcon);
        Uri icoUri = MainActivity.user.getIconUri();
        if(icoUri != null) {
            ico.setImageURI(icoUri);
        }

        Button favPrograms = view.findViewById(R.id.homePage_trainBtn);
        favPrograms.setOnClickListener(v -> {
            replaceFragment(new FavouriteProgramsFragment());
        });

        Button progress = view.findViewById(R.id.homePage_progrressBtn);
        progress.setOnClickListener(v -> {
            replaceFragment(new ChroniclesOfProgressFragment());
        });

        Button settigs = view.findViewById(R.id.homePage_settingsBtn);
        settigs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            getContext().startActivity(intent);
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