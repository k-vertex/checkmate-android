package com.example.checkmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends Fragment {
    private View view;
    private LocalDate now;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attendance, container, false);
        now = LocalDate.now();

        ImageView preMonthArrow = view.findViewById(R.id.preMonthArrow);
        preMonthArrow.setOnClickListener(view -> {
            now = now.minusMonths(1);
            setYearAndMonth();
            setDay();
        });

        ImageView nextMonthArrow = view.findViewById(R.id.nextMonthArrow);
        nextMonthArrow.setOnClickListener(view -> {
            now = now.plusMonths(1);
            setYearAndMonth();
            setDay();
        });

        setYearAndMonth();
        setDay();
        return view;
    }

    private void setYearAndMonth() {
        TextView monthView = view.findViewById(R.id.calendarMonth);
        monthView.setText(now.getYear() + "년 " + now.getMonthValue() + "월");
    }

    private void setDay() {
        List<String> dayList = daysInMonthArray(now);
        CalendarAdapter adapter = new CalendarAdapter(dayList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private List<String> daysInMonthArray(LocalDate date) {
        List<String> dayList = new ArrayList<String>();
        YearMonth yearMonth = YearMonth.from(date);
        int lastDay = yearMonth.lengthOfMonth();

        LocalDate firstDay = date.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for(int i = 0; i < 42; i++) {
            if(i < dayOfWeek || i >= lastDay + dayOfWeek)
                dayList.add("");
            else
                dayList.add(String.valueOf((i - dayOfWeek) + 1));
        }
        return dayList;
    }
}
