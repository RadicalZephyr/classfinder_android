package edu.wwu.classfinder2.test.data;

import android.support.v4.util.ArrayMap;

import edu.wwu.classfinder2.data.ScheduleParser;
import edu.wwu.classfinder2.data.Schedule.Meeting;

import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joda.time.DateTimeConstants;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.LocalTime;

public class ScheduleParserTest extends TestCase {

    public void testParse() {
        Map<String, Meeting[]> cases = new ArrayMap<String, Meeting[]>();
        cases.put("MT 10:00-10:50 am",
                  new Meeting[] {
                      new Meeting(DateTimeConstants.MONDAY,
                                  new LocalTime(10,0),
                                  new Period(0, 50, 0, 0)),
                      new Meeting(DateTimeConstants.TUESDAY,
                                  new LocalTime(10,0),
                                  new Period(0, 50, 0, 0))});
        cases.put("TR 12:00-01:50 pm",
                  new Meeting[] {
                      new Meeting(DateTimeConstants.TUESDAY,
                                  new LocalTime(12,0),
                                  new Period(0, 110, 0, 0)),
                      new Meeting(DateTimeConstants.THURSDAY,
                                  new LocalTime(12,0),
                                  new Period(0, 110, 0, 0))});
        cases.put("T 00:00-04:00 am",
                  new Meeting[] {
                      new Meeting(DateTimeConstants.TUESDAY,
                                  new LocalTime(0,0),
                                  new Period(4, 0, 0, 0))});
        cases.put("F 01:50-03:50 pm;S 12:30-12:50 pm",
                  new Meeting[] {
                      new Meeting(DateTimeConstants.FRIDAY,
                                  new LocalTime(13,50),
                                  new Period(2, 0, 0, 0)),
                      new Meeting(DateTimeConstants.SATURDAY,
                                  new LocalTime(12,30),
                                  new Period(0, 20, 0, 0))});
        cases.put("W 01:00-07:50 pm;U 12:30-01:00 pm;M 02:00-01:00 pm",
                  new Meeting[] {
                      new Meeting(DateTimeConstants.WEDNESDAY,
                                  new LocalTime(13,0),
                                  new Period(6, 50, 0, 0)),
                      new Meeting(DateTimeConstants.SUNDAY,
                                  new LocalTime(12,30),
                                  new Period(0, 30, 0, 0)),
                      new Meeting(DateTimeConstants.MONDAY,
                                  new LocalTime(2,0),
                                  new Period(11, 0, 0, 0))});

        for (Map.Entry<String, Meeting[]> entry : cases.entrySet()) {
            ScheduleParser sp = new ScheduleParser(entry.getKey());
            Meeting[] meetings = entry.getValue();
            int i = 0;
            for (Meeting m : sp) {
                Assert.assertTrue("", i < meetings.length);
                Assert.assertEquals("Meetings should parse equivalently",
                                    meetings[i++],
                                    m);
            }
        }
    }

}
