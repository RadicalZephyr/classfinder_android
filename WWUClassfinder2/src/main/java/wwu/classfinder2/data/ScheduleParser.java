package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.data.Schedule.Meeting;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;
import org.joda.time.LocalTime;

public class ScheduleParser
    implements Iterable<Meeting> {

    private final String[] mScheduleStrs;

    // Schedule strings with multiple different day/time combinations
    // should be have each combination delimited by semi-colons, and
    // no other semi-colons should be present
    // i.e. "MWF 02:00-2:50 pm;T 10:00-11:50 am"
    public ScheduleParser(String scheduleStr) {
        mScheduleStrs = scheduleStr.split(";");
    }

    public String[] getScheduleStrs() {
        return mScheduleStrs;
    }

    public Iterator<Meeting> iterator() {
        return this.new ParserIterator();
    }

    public class ParserIterator
        implements Iterator<Meeting> {

        private int mListIndex = 0;
        private int mDayIndex = 0;

        private String mCurrentDays;
        private LocalTime mCurrentStartTime;
        private Period mCurrentPeriod;

        @Override
        public boolean hasNext() {
            return (mListIndex < mScheduleStrs.length);
        }

        @Override
        public Meeting next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // Initialize the current strings
            if (mCurrentDays == null
                || mCurrentStartTime == null
                || mCurrentPeriod == null) {
                String curSchedule = mScheduleStrs[mListIndex];
                int index = curSchedule.indexOf(' ');
                mCurrentDays = curSchedule.substring(0, index);
                timeFromString(curSchedule
                               .substring(index+1,
                                          curSchedule.length()));
                mDayIndex = 0;
            }

            // Get the day char and increment the dayIndex
            char dayChar = mCurrentDays.charAt(mDayIndex++);

            Meeting meeting = new Meeting(dayFromCharacter(dayChar),
                                          mCurrentStartTime,
                                          mCurrentPeriod);

            // Check if out of days for current time and period
            if (mDayIndex >= mCurrentDays.length()) {
                mCurrentDays      = null;
                mCurrentStartTime = null;
                mCurrentPeriod  = null;
                // and go to next list element
                mListIndex++;
                mDayIndex = 0;
            }

            return meeting;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private int dayFromCharacter(char dayChar) {
            switch (dayChar) {
            case 'U': return DateTimeConstants.SUNDAY;
            case 'M': return DateTimeConstants.MONDAY;
            case 'T': return DateTimeConstants.TUESDAY;
            case 'W': return DateTimeConstants.WEDNESDAY;
            case 'R': return DateTimeConstants.THURSDAY;
            case 'F': return DateTimeConstants.FRIDAY;
            case 'S': return DateTimeConstants.SATURDAY;
            default:
                throw new IllegalArgumentException(String
                                                   .format("dayChar: %c",
                                                           dayChar));
            }
        }

        private void timeFromString(String periodString) {
            int durStringLen = periodString.length();

            char amPm = periodString.charAt(durStringLen-2);

            // Remove the " {a,p}m" from the end, and split on
            // the "-".
            String[] startAndEndTime =
                periodString.substring(0, durStringLen-3)
                .split("-");

            mCurrentStartTime = LocalTime.parse(startAndEndTime[0]);
            LocalTime endTime = LocalTime.parse(startAndEndTime[1]);

            if (amPm == 'p') {
                // Getting this part of the parse correct is REALLY
                // HARD because classfinder uses a VERY ambiguous
                // format for class times.
                //
                // As a hack, we'll make a BIG assumption.

                // When endTime is "earlier" than startTime, start is
                // an AM time and end is a PM.
                // OTHERWISE: both are pm times when there's a "p"
                boolean startShouldAdd = false;
                boolean endShouldAdd   = false;
                if (endTime.isBefore(mCurrentStartTime)) {
                    startShouldAdd = false;
                    endShouldAdd   = true;
                } else {
                    startShouldAdd = true;
                    endShouldAdd   = true;
                }

                // These are effectively only testing for the 12-1 pm
                // weirdness.  Because anything from 01:00 to 11:59 pm
                // will be parsed as "before" noon.
                if (mCurrentStartTime.isAfter(LocalTime.MIDNIGHT
                                              .plusHours(12)))
                    startShouldAdd = false;
                if (endTime.isAfter(LocalTime.MIDNIGHT.plusHours(12)))
                    endShouldAdd = false;

                if (startShouldAdd)
                    mCurrentStartTime = mCurrentStartTime.plusHours(12);
                if (endShouldAdd)
                    endTime = endTime.plusHours(12);
            }

            mCurrentPeriod = new Period(mCurrentStartTime,
                                        endTime);
        }

    }
}
