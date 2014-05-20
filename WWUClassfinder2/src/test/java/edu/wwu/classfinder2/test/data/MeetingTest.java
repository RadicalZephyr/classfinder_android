package edu.wwu.classfinder2.test.data;

import edu.wwu.classfinder2.data.Schedule.Meeting;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.LocalTime;

public class MeetingTest extends TestCase {

    public void testMeeting() {
        Meeting m = new Meeting(DateTimeConstants.MONDAY,
                                new LocalTime(13,0),
                                new Period(0, 50, 0, 0));

        Assert.assertEquals("M 01:00-01:50 pm", m.toString());
    }

}
