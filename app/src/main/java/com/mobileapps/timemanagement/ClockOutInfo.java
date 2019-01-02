package com.mobileapps.timemanagement;

public class ClockOutInfo {

    String clockOutId;
    String clockOutTime;
    String clockOutDay;

    public ClockOutInfo(){

    }

    public ClockOutInfo(String clockOutId, String clockOutTime, String clockOutDay) {
        this.clockOutId = clockOutId;
        this.clockOutTime = clockOutTime;
        this.clockOutDay = clockOutDay;
    }

    public String getClockOutId() {
        return clockOutId;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public String getClockOutDay() {
        return clockOutDay;
    }
}
