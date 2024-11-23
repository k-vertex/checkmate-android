package com.example.checkmate.attend;

import java.time.LocalDate;
public class Attendance {

    private int studentID;
    private LocalDate date;
    private boolean checked;

    public Attendance(int studentID, LocalDate date, boolean checked) {
        this.studentID = studentID;
        this.date = date;
        this.checked = checked;
    }

    public int getStudentID() { return studentID; }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isChecked() { return checked; }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
