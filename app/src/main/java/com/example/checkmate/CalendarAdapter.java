package com.example.checkmate;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.calendarViewHolder> {

    private List<String> dayList;

    public CalendarAdapter(List<String> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public calendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        int screenHeight = parent.getMeasuredHeight();
        int rowCount = 6;
        int itemHeight = screenHeight / rowCount;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        return new calendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull calendarViewHolder holder, int position) {
        String day = dayList.get(position);
        holder.dayText.setText(day);
        if(day.equals("")) {
            ViewGroup parent = (ViewGroup) holder.attendCircle.getParent();
            parent.removeView(holder.attendCircle);
        }
        if(position % 7 == 6)
            holder.dayText.setTextColor(Color.parseColor("#0000DB"));
        else if(position % 7 == 0)
            holder.dayText.setTextColor(Color.parseColor("#DB0000"));
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class calendarViewHolder extends RecyclerView.ViewHolder {

        TextView dayText;
        LinearLayout attendCircle;

        public calendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
            attendCircle = itemView.findViewById(R.id.attendCircle);
        }
    }
}
