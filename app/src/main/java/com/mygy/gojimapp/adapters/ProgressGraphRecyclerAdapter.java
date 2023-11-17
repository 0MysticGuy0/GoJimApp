package com.mygy.gojimapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.mygy.gojimapp.R;

import com.mygy.gojimapp.data.ProgressParameter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProgressGraphRecyclerAdapter extends RecyclerView.Adapter<ProgressGraphRecyclerAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private ProgressParameter parameters;

    public ProgressGraphRecyclerAdapter(Context context, ProgressParameter parameters) {
        this.parameters = parameters;
        this.inflater = LayoutInflater.from(context);
    }
    public void setParameter(ProgressParameter parameter){
            parameters=parameter;
    }
    @Override
    public ProgressGraphRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.progress_graph, parent, false);
        return new ProgressGraphRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgressGraphRecyclerAdapter.ViewHolder holder, int position) {
        String parameterName = parameters.getParametersInfo().get(position).getParameterName();

        if(parameters != null) {
            ArrayList<Entry> lineList = new ArrayList<>();
            holder.parameterName.setText(parameterName);

            XAxis xaxis = holder.graph.getXAxis();
            //xaxis.setGranularity(1);
            xaxis.setDrawGridLines(false);
            xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis yaxis = holder.graph.getAxisLeft();
            //yaxis.setAxisMinimum(0.0f);

            for (int i = 0; i< parameters.getParameters().size(); i++) {
                ProgressParameter.Parameter p = parameters.getParameters().get(i);
                float val = (float)(double) p.getValues().get(position);
                lineList.add(new Entry(i, val));
            }
            LineDataSet dataSet = new LineDataSet(lineList, parameterName);
            //dataSet.setLineWidth(18);
            dataSet.setColor(Color.RED);
            dataSet.setValueTextSize(15);

            LineData lineData = new LineData(dataSet);
            holder.graph.setData(lineData);
            holder.graph.invalidate();
        }
    }

    @Override
    public int getItemCount() {
        if(parameters == null) return 0;
        return parameters.getParametersInfo().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView parameterName;
        final LineChart graph;
        ViewHolder(View view){
            super(view);
            parameterName = view.findViewById(R.id.progressGraph_name);
            graph = view.findViewById(R.id.progressGraph_graph);
        }
    }
}
