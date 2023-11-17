package com.mygy.gojimapp.data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.mygy.gojimapp.MainActivity;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.adapters.ProgressInputRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ProgressParameter implements Serializable {

    protected List<ParameterInfo> parametersInfo;//информация о параметрах(имя и единица измерения)
    protected Group parameterGroup;
    private String name;
    private ArrayList<Parameter> parameters = new ArrayList<>();//сохраненные показатели
    public final static ArrayList<ExerciseProgressParameter> allExercisesParameters = new ArrayList<>();
    public final static ArrayList<ProgressParameter> allBodyParameters = new ArrayList<>();
    public final static ArrayList<ProgressParameter> allOtherParameters = new ArrayList<>();

    public ProgressParameter(String name, Group parameterGroup, ParameterInfo... parametersInfo) {
        this.name = name;

        this.parametersInfo = Arrays.asList(parametersInfo);
        this.parameterGroup = ((parameterGroup == null) ? Group.OTHER : parameterGroup);

        switch (parameterGroup) {
            case EXERCISES:
                if(this instanceof ExerciseProgressParameter) {
                    allExercisesParameters.add((ExerciseProgressParameter) this);
                }
                break;
            case BODY_PARAMETERS:
                allBodyParameters.add(this);
                break;
            case OTHER:
                allOtherParameters.add(this);
                break;
        }
    }
    public static ProgressParameter getParameterFromDoc(HashMap<String, Object> doc,Group group){
        String name = (String)doc.get("name");
        ProgressParameter res = null;
        switch(group){
            case BODY_PARAMETERS:
                res = getBodyParameterByName(name);
                break;
            case EXERCISES:
                res = getExercisesParameterByName(name);
                break;
            case OTHER:
                res = getOtherParameterByName(name);
                break;
        }
        if(res != null) return res;

        if(group == Group.EXERCISES){
            res = ExerciseProgressParameter.getExerciseProgressParameterFromDoc(doc);
        }
        else{
            try {
                List<HashMap<String, Object>> paramsInf_docsList = (List<HashMap<String, Object>>) doc.get("parametersInfo");
                ParameterInfo[] paramsInf_array = new ParameterInfo[paramsInf_docsList.size()];
                for (int i = 0; i < paramsInf_array.length; i++) {
                    paramsInf_array[i] = new ParameterInfo(paramsInf_docsList.get(i));
                }
                res = new ProgressParameter(name, group, paramsInf_array);

                List<HashMap<String, Object>> params_docsList = (List<HashMap<String, Object>>) doc.get("parameters");
                for (HashMap<String, Object> p : params_docsList) {
                    res.addParameter(new Parameter(p));
                }
            }catch (NullPointerException ex){
                res = null;
            }
        }
        return res;
    }
    /////////////////////////////////////////----------------------------------------
    public String getName() {
        return name;
    }
    public ArrayList<Parameter> getParameters() {
        return parameters;
    }
    public List<ParameterInfo> getParametersInfo() {
        return parametersInfo;
    }

    public static ProgressParameter getBodyParameterByName(String name){
        for(ProgressParameter p: allBodyParameters){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }
    public static ProgressParameter getOtherParameterByName(String name){
        for(ProgressParameter p: allOtherParameters){
            if(p.getName().equals(name)) return p;
        }
        return null;
    }
    public static ExerciseProgressParameter getExercisesParameterByName(String name){
        for(ExerciseProgressParameter p: allExercisesParameters){
            if(p.getName().equals(name)) return (ExerciseProgressParameter) p;
        }
        return null;
    }

    /////////////////////////////////////////----------------------------------------

    public Parameter addParameter(Parameter parameter) {
        if (parameter.getDate() == null || parameter.getValues() == null || parameter.getValues().size() != parametersInfo.size())
            return null;

        //сортировка по дате
        int index;
        for(index = 0; index < parameters.size(); index++){
            if(parameters.get(index).getDate().after(parameter.getDate())){
                break;
            }
        }

        parameters.add(index,parameter);
        if(MainActivity.user != null)
            MainActivity.user.updateParametersInDoc(parameterGroup);
        return parameter;
    }

    public static AlertDialog createSaveParameterPopup(ProgressParameter parameter, FragmentActivity fragmentActivity){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(fragmentActivity);
        final View saveParameterView= fragmentActivity.getLayoutInflater().inflate(R.layout.save_parameter_popup,null);

        TextView parameterName = saveParameterView.findViewById(R.id.saveParameter_parameterName);
        parameterName.setText("\""+parameter.getName()+"\"");

        RecyclerView inputRecycler = saveParameterView.findViewById(R.id.saveParameter_inputRecycler);
        ProgressInputRecyclerAdapter inputAdapter = new ProgressInputRecyclerAdapter(fragmentActivity,parameter.getParametersInfo());
        inputRecycler.setAdapter(inputAdapter);
        EditText[] inputsET = inputAdapter.getInputs();

        ImageButton cancel = saveParameterView.findViewById(R.id.saveParameter_cancelBtn);
        ImageButton add = saveParameterView.findViewById(R.id.saveParameter_addBtn);

        final Date[] selectedDate = new Date[1];
        Button dateBtn = saveParameterView.findViewById(R.id.saveParameter_dateBtn);
        dateBtn.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog dpd = new DatePickerDialog(fragmentActivity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selectedDate[0] = new Date(year-1900,month,dayOfMonth);
                    dateBtn.setText(dayOfMonth+"/"+month+"/"+year);
                }
            },year,month,day);
            dpd.show();
        });

        a_builder.setView(saveParameterView);
        AlertDialog dialog = a_builder.create();
        dialog.show();

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        add.setOnClickListener(v -> {
            try{
                if(selectedDate[0] == null) {
                    Toast.makeText(fragmentActivity,"Не выбрана дата!!!",Toast.LENGTH_SHORT).show();
                }
                Double values[] = new Double[inputsET.length];
                for(int i =0;i<inputsET.length;i++){
                    values[i] = Double.parseDouble(inputsET[i].getText().toString());
                }
                parameter.addParameter(new Parameter(selectedDate[0],values));
                Toast.makeText(fragmentActivity,"Сохранено",Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }catch (NumberFormatException ex){
                Toast.makeText(fragmentActivity,"Не верно введены параметры!!!",Toast.LENGTH_SHORT).show();
            }

        });
        return dialog;
    }
    /////////////////////////////////////////----------------------------------------
    public static enum Group implements Serializable {
        EXERCISES,
        BODY_PARAMETERS,
        OTHER;

        @NonNull
        @Override
        public String toString() {
            String result = "none";
            switch (this) {
                case EXERCISES:
                    result = "упражнения";
                    break;
                case BODY_PARAMETERS:
                    result = "замеры тела";
                    break;
                case OTHER:
                    result = "другое";
                    break;
            }
            return result;
        }


    }
    /////////////////////////////////////////----------------------------------------
    public static class Parameter implements Serializable {
        protected List<Double> values;
        protected Date date;

        public Parameter(Date date, Double... values) {
            this.values = Arrays.asList(values);
            this.date = date;
        }
        public Parameter(HashMap<String,Object> doc){
            date = ((Timestamp)doc.get("date")).toDate();
            List<Object> temp = (List<Object>)doc.get("values");
            values = new ArrayList<>();
            for(Object n : temp){
                if(n instanceof Double)
                    values.add((Double) n);
                else
                    values.add(Double.valueOf((Long)n));
            }

        }

        public List<Double> getValues() {
            return values;
        }

        public Date getDate() {
            return date;
        }
    }

    public static class ParameterInfo implements Serializable {
        private final String parameterName;
        private final String parameterUnit;//единица измерения

        public ParameterInfo(String parameterName, String parameterUnit) {

            this.parameterName = parameterName;
            this.parameterUnit = parameterUnit;
        }
        public ParameterInfo(HashMap<String,Object> doc){
            parameterName = (String) doc.get("parameterName");
            parameterUnit = (String) doc.get("parameterUnit");
        }

        public String getParameterName() {
            return parameterName;
        }
        public String getParameterUnit() {
            return parameterUnit;
        }
    }
}