package edu.wwu.classfinder2.data;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;
import org.joda.time.LocalTime;

public class Schedule {

    private List<Meeting> mMeetings;

    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }

    public static Schedule fromString(String scheduleStr) {
        Schedule schedule = new Schedule();
        List<Meeting> meetings = new ArrayList<Meeting>();
        CollectionUtils.addAll(meetings, new ScheduleParser(scheduleStr));
        schedule.setMeetings(meetings);
        return schedule;
    }

    public String asString() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Schedule)) {
            return false;
        }

        Schedule oSchedule = (Schedule) obj;
        return new EqualsBuilder()
            .append(mMeetings, oSchedule.mMeetings)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(mMeetings)
            .toHashCode();
    }

    public static class Meeting {

        private int mDay;

        private LocalTime mStartTime;

        private Period mPeriod;

        public Meeting(int day,
                       LocalTime startTime,
                       Period period) {
            if (day == -1)
                throw new IllegalArgumentException("Day of week is null.");
            mDay = day;
            if (startTime == null)
                throw new IllegalArgumentException("Start time is null.");
            mStartTime = startTime;
            if (period == null)
                throw new IllegalArgumentException("Period is null.");
            mPeriod = period;
        }

        public int getDay() {
            return mDay;
        }

        public LocalTime getStartTime() {
            return mStartTime;
        }

        public Period getPeriod() {
            return mPeriod;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj == this
                || !(obj instanceof Meeting)) {
                return false;
            }

            Meeting oMeeting = (Meeting) obj;
            return new EqualsBuilder()
                .append(mDay, oMeeting.mDay)
                .append(mStartTime, oMeeting.mStartTime)
                .append(mPeriod, oMeeting.mPeriod)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31)
                .append(mDay)
                .append(mStartTime)
                .append(mPeriod)
                .toHashCode();
        }

        @Override
        public String toString() {
            char dayChar;
            switch (mDay) {
            case DateTimeConstants.SUNDAY:
                dayChar = 'U';
                break;
            case DateTimeConstants.MONDAY:
                dayChar = 'M';
                break;
            case DateTimeConstants.TUESDAY:
                dayChar = 'T';
                break;
            case DateTimeConstants.WEDNESDAY:
                dayChar = 'W';
                break;
            case DateTimeConstants.THURSDAY:
                dayChar = 'R';
                break;
            case DateTimeConstants.FRIDAY:
                dayChar = 'F';
                break;
            case DateTimeConstants.SATURDAY:
                dayChar = 'S';
                break;
            default:
                dayChar = 'X';
                break;
            }

            LocalTime start = mStartTime;
            String amOrPm;
            LocalTime end = start.plus(mPeriod);
            LocalTime onePm =
                LocalTime.MIDNIGHT.plusHours(12).plusMinutes(59);

            if (end.isAfter(LocalTime.MIDNIGHT
                            .plusHours(11).plusMinutes(59))) {
                amOrPm = "pm";
                if (start.isAfter(onePm))
                    start = start.minusHours(12);
                if (end.isAfter(onePm))
                    end = end.minusHours(12);
            } else {
                amOrPm = "am";
                start = mStartTime;
            }

            return String.format("%c %s-%s %s",
                                 dayChar,
                                 start.toString("HH:mm"),
                                 end.toString("HH:mm"),
                                 amOrPm);
        }
    }


}
