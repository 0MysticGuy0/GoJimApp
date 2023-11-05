package com.mygy.gojimapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mygy.gojimapp.MainActivity;
import com.mygy.gojimapp.R;
import com.mygy.gojimapp.TrainingProgramActivity;
import com.mygy.gojimapp.data.TrainingProgramm;

import java.util.List;

public class TrainingProgramRecyclerAdapter extends RecyclerView.Adapter<TrainingProgramRecyclerAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<TrainingProgramm> programs;

    public TrainingProgramRecyclerAdapter(Context context, List<TrainingProgramm> programs) {
        this.programs = programs;
        this.inflater = LayoutInflater.from(context);
        System.out.println("FFFFFFFFFfffff"+programs.size());
    }
    @Override
    public TrainingProgramRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.training_programm_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrainingProgramRecyclerAdapter.ViewHolder holder, int position) {
        TrainingProgramm program = programs.get(position);

        holder.name.setText(program.getName());
        holder.daysCount.setText(Integer.toString(program.getTrainingDays().size()));

        if(MainActivity.user.getFavPrograms().contains(program))
            holder.favBtn.setImageResource(R.drawable.baseline_star_24_gold);

        holder.favBtn.setOnClickListener(v -> {
            if(!MainActivity.user.getFavPrograms().contains(program)) {
                MainActivity.user.addFavProgram(program);
                holder.favBtn.setImageResource(R.drawable.baseline_star_24_gold);
            }
            else {
                MainActivity.user.getFavPrograms().remove(program);
                holder.favBtn.setImageResource(R.drawable.baseline_star_24);
            }
        });

        holder.root.setOnClickListener(v -> {
            Intent intent = new Intent(inflater.getContext(), TrainingProgramActivity.class);
            intent.putExtra(TrainingProgramm.class.getSimpleName(), program);
            inflater.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView programIco;
        final TextView name, daysCount;
        final ConstraintLayout root;
        final ImageButton favBtn;
        ViewHolder(View view){
            super(view);
            programIco = view.findViewById(R.id.trainingProgramButton_Ico);
            name = view.findViewById(R.id.trainingProgramButton_Name);
            daysCount = view.findViewById(R.id.trainingProgramButton_trainingDays);
            root= view.findViewById(R.id.trainingProgramButton_Root);
            favBtn = view.findViewById(R.id.trainingProgramButton_FavBtn);
        }
    }
}
