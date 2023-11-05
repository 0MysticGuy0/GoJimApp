package com.mygy.gojimapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mygy.gojimapp.R;
import com.mygy.gojimapp.data.TrainingDay;

import java.util.ArrayList;
import java.util.List;

public class TrainingDayRecyclerAdapter extends RecyclerView.Adapter<TrainingDayRecyclerAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<TrainingDay> days;
    private boolean addDayCounter = false;

    public TrainingDayRecyclerAdapter(Context context, List<TrainingDay> days,boolean addDayCounter) {
        this.days = days;
        this.inflater = LayoutInflater.from(context);
        this.addDayCounter=addDayCounter;
    }
    @Override
    public TrainingDayRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.training_day_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainingDayRecyclerAdapter.ViewHolder holder, int position) {
        TrainingDay day = days.get(position);

        String name = (addDayCounter)?"День "+(position+1)+". "+day.getName():day.getName();
        holder.dayName.setText(name);
        ExerciseRecyclerAdapter adapter = new ExerciseRecyclerAdapter(inflater.getContext(),new ArrayList<>());
        holder.recycler.setAdapter(adapter);
        holder.showBtn.setOnClickListener(v -> {
            if(adapter.getExercises().size()>0) {
                holder.showBtn.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                adapter.setExercises(new ArrayList<>());
            }
            else{
                holder.showBtn.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                adapter.setExercises(day.getExercises());
            }
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dayName;
        final RecyclerView recycler;
        final ImageButton showBtn;
        ViewHolder(View view){
            super(view);
            dayName = view.findViewById(R.id.trainingDayBtn_name);
            recycler = view.findViewById(R.id.trainingDayBtn_recycler);
            showBtn = view.findViewById(R.id.trainingDayBtn_showBtn);
        }
    }
}
