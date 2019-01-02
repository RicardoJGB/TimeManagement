package com.mobileapps.timemanagement;

public class ClockInInfo {

    String clockInId;
    String clockInTime;
    String clockInDay;

    public ClockInInfo(){

    }

    public ClockInInfo(String clockInId, String clockInTime, String clockInDay) {
        this.clockInId = clockInId;
        this.clockInTime = clockInTime;
        this.clockInDay = clockInDay;
    }

    public String getClockInId() {
        return clockInId;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public String getClockInDay() {
        return clockInDay;
    }
}
