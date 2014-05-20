package edu.wwu.classfinder2.test.data;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import edu.wwu.classfinder2.data.Schedule;
import edu.wwu.classfinder2.data.Schedule.Meeting;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;
import org.joda.time.LocalTime;

public class ScheduleTest extends TestCase {

    public void testEquals() {
        Schedule sched = Schedule.fromString("M 11:00-01:50 pm");
        List<Meeting> meetings = sched.getMeetings();

        Assert.assertNotNull("Should have some meetings.", meetings);
        Assert.assertEquals("Should have exactly one meeting.",
                            1, meetings.size());

        Meeting meeting = meetings.get(0);

        Assert.assertNotNull("The meeting shouldn't be null.", meeting);
        Assert.assertEquals("Meeting is on Monday.",
                            DateTimeConstants.MONDAY, meeting.getDay());

    }

}
