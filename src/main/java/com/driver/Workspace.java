package com.driver;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar; // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId,Integer.MAX_VALUE);
        this.calendar=new ArrayList<>();
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        calendar.add(meeting);
    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        Collections.sort(calendar,(a,b)->{
            if(a.getEndTime().compareTo(b.getEndTime())<0) return 1;
            else return 0;
        });

        LocalTime endTime=LocalTime.parse("00:00");
        int cnt=0;
        for(int i=0;i<calendar.size();i++){
            if(calendar.get(i).getStartTime().compareTo(endTime)>0) {
                cnt++;
                endTime=calendar.get(i).getEndTime();
            }
        }
        return cnt;
    }
}
