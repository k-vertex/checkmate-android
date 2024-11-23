package com.example.checkmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkmate.attend.Attendance;

import java.util.HashMap;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.calendarViewHolder> {

    private List<String> dayList;
    private HashMap<Integer, Boolean> attendanceMap;

    public CalendarAdapter(List<String> dayList, HashMap<Integer, Boolean> attendanceMap) {
        this.dayList = dayList;
        this.attendanceMap = attendanceMap;
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
        else {
            int dayInt = Integer.parseInt(day);
            if(attendanceMap.containsKey(dayInt)) {
                System.out.println(dayInt);
                if (attendanceMap.get(dayInt))
                    holder.drawAttendanceCircle();
                else
                    holder.drawAbsenceCircle();
            }
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

        public void drawAttendanceCircle() {
            Context context = attendCircle.getContext();
            LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.circle).mutate();
            GradientDrawable backgroundLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circle);
            GradientDrawable borderLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circleBorder);
            backgroundLayer.setColor(Color.parseColor("#7BCDF2"));
            borderLayer.setStroke(1, Color.parseColor("#7BCDF2"));
            attendCircle.setBackground(layerDrawable);

            TextView textView = attendCircle.findViewById(R.id.attendanceCircleText);
            textView.setText("출석");
        }

        public void drawAbsenceCircle() {
            Context context = attendCircle.getContext();
            LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.circle).mutate();
            GradientDrawable backgroundLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circle);
            GradientDrawable borderLayer = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circleBorder);
            backgroundLayer.setColor(Color.parseColor("#999999"));
            borderLayer.setStroke(1, Color.parseColor("#999999"));
            attendCircle.setBackground(layerDrawable);

            TextView textView = attendCircle.findViewById(R.id.attendanceCircleText);
            textView.setText("결석");
        }
    }
}
