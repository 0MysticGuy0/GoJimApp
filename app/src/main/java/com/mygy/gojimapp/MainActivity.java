package com.mygy.gojimapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mygy.gojimapp.data.ExerciseProgressParameter;
import com.mygy.gojimapp.data.ProgressParameter;
import com.mygy.gojimapp.data.Store;
import com.mygy.gojimapp.data.TrainingProgramm;
import com.mygy.gojimapp.data.User;
import com.mygy.gojimapp.trainingPage.ExercisesLibFragment;

public class MainActivity extends AppCompatActivity {

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Store.squat.getName();//чтобы вызвать статик-блок в сторадж // НУЖНО ИСПРАВИТЬ!!!

        System.out.println("LoadedusersDoc++++++\n"+user.getUserDocument()+"\n++++++");


        BottomNavigationView bnv=findViewById(R.id.bottomNavView);
        bnv.setOnItemSelectedListener(item ->{
            if(item.getItemId()==R.id.mainNavBtn)
                replaceFragment(new HomeFragment());
            else if(item.getItemId()==R.id.trainNavBtn)
                replaceFragment(new TrainingFragment());
            return true;
        });
    }

    private void replaceFragment(Fragment newFragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFragmentContainerView,newFragment);
        ft.commit();
    }
}