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

import com.example.checkmate.attend.Attendance;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

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
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://emperorchang.store:8888/attend";
        RequestParams params = new RequestParams();
        params.put("studentID", "1");
        params.put("year", now.getYear());
        params.put("month", now.getMonthValue());

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                HashMap<Integer, Boolean> attendanceMap = new HashMap<Integer, Boolean>();
                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String dateString = object.getString("date");
                        boolean checked = object.getInt("checked") == 1;
                        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        int day = date.getDayOfMonth();
                        attendanceMap.put(day, checked);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                List<String> dayList = daysInMonthArray(now);
                CalendarAdapter adapter = new CalendarAdapter(dayList, attendanceMap);
                RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
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
