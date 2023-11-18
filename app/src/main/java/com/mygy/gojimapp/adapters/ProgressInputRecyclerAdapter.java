package com.mygy.gojimapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.data.ProgressParameter;

import java.util.List;


public class ProgressInputRecyclerAdapter extends RecyclerView.Adapter<ProgressInputRecyclerAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<ProgressParameter.ParameterInfo> parameterInfo;
    private EditText[] inputs;

    public ProgressInputRecyclerAdapter(Context context, List<ProgressParameter.ParameterInfo> parameterInfo) {
        this.parameterInfo = parameterInfo;
        this.inputs = new EditText[parameterInfo.size()];
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ProgressInputRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.parameter_input_item, parent, false);
        return new ProgressInputRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgressInputRecyclerAdapter.ViewHolder holder, int position) {
        ProgressParameter.ParameterInfo paramInf = parameterInfo.get(position);
        holder.parameterName.setText(paramInf.getParameterName());
        holder.parameterUnit.setText(paramInf.getParameterUnit());
        inputs[position] = holder.input;

        //InputMethodManager inputMananger = (InputMethodManager) inflater.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputMananger.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
       // inputs[position].requestFocusFromTouch();
    }

    @Override
    public int getItemCount() {
        return parameterInfo.size();
    }
    public EditText[] getInputs(){return inputs;}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView parameterName, parameterUnit;
        final EditText input;
        ViewHolder(View view){
            super(view);
            parameterName = view.findViewById(R.id.parameterInput_name);
            parameterUnit = view.findViewById(R.id.parameterInput_unit);
            input = view.findViewById(R.id.parameterInput_input);
        }
    }
}
