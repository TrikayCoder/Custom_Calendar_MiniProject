package com.project.customcalendarminiproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private ArrayList<String> daysOfMonth;

    private String dateToday;

    private Context context;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        View view = inflater.inflate(R.layout.item_calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(dateToday != null){
            if(dateToday.equals(daysOfMonth.get(position))){
               //Set state of date cell if this is day user choose

            }
        }
        holder.dayOfMonth.setText(daysOfMonth.get(position));

        holder.dayOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User choode date events
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void cvBtnTodayEvent(String day){
        dateToday = day;
        notifyDataSetChanged();
    }


    public String getDateToday() {

        return "fff";
    }

    public void setDateToday(String dateToday) {
        this.dateToday = dateToday;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dayOfMonth;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
        }

    }
}
