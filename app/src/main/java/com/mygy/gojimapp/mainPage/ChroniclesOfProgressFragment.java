package com.mygy.gojimapp.mainPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.data.Exercise;
import com.mygy.gojimapp.data.ProgressParameter;

import java.util.Arrays;

public class ChroniclesOfProgressFragment extends Fragment {
    public final String[] parametersGroups = new String[]{ProgressParameter.Group.EXERCISES.toString(),ProgressParameter.Group.BODY_PARAMETERS.toString(), ProgressParameter.Group.OTHER.toString()};
    private ArrayAdapter<String> parametersAdapter_exercises,parametersAdapter_bodyParams,parametersAdapter_other;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_chronicles_of_progress, container, false);

        String[] allExercises = new String[ProgressParameter.allExercisesParameters.size()];
        for(int i = 0; i< allExercises.length;i++){
            allExercises[i]=ProgressParameter.allExercisesParameters.get(i).getName();
        }
        Arrays.sort(allExercises);
        String[] allBodyParameters = new String[ProgressParameter.allBodyParameters.size()];
        for(int i = 0; i< allBodyParameters.length;i++){
            allBodyParameters[i]=ProgressParameter.allBodyParameters.get(i).getName();
        }
        String[] allOtherParameters = new String[ProgressParameter.allOtherParameters.size()];
        for(int i = 0; i< allOtherParameters.length;i++){
            allOtherParameters[i]=ProgressParameter.allOtherParameters.get(i).getName();
        }
        parametersAdapter_exercises = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,allExercises);
        parametersAdapter_exercises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        parametersAdapter_bodyParams = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,allBodyParameters);
        parametersAdapter_bodyParams.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        parametersAdapter_other = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,allOtherParameters);
        parametersAdapter_other.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Spinner groupSpinner = view.findViewById(R.id.progress_GroupSpinner);
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,parametersGroups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);

        Spinner parameterSpinner = view.findViewById(R.id.progress_ParameterSpinner);
        parameterSpinner.setAdapter(parametersAdapter_exercises);

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item =(String)adapterView.getItemAtPosition(position);

                if(item.equals( ProgressParameter.Group.EXERCISES.toString() )) {
                    parameterSpinner.setAdapter(parametersAdapter_exercises);
                }
                else if(item.equals( ProgressParameter.Group.BODY_PARAMETERS.toString() )) {
                    parameterSpinner.setAdapter(parametersAdapter_bodyParams);
                }
                else if(item.equals( ProgressParameter.Group.OTHER.toString() )) {
                    parameterSpinner.setAdapter(parametersAdapter_other);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //НУЖНО СДЕЛАТЬ СВОЙ АДАПТЕР!!!!!!!!!!!!!!!!!!
        parameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parameterSpinner.getAdapter();

                if(adapter == parametersAdapter_exercises){
                    Exercise selectedExercise = Exercise.getExerciseByName((String)adapterView.getItemAtPosition(position));
                }
                else if(adapter == parametersAdapter_bodyParams){

                }
                else if(adapter == parametersAdapter_other){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
       return view;
    }
}