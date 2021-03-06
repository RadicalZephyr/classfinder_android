package edu.wwu.classfinder2.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.wwu.classfinder2.provider.ClassfinderContract.CourseContract;

public class Course {

    public static final int WINTER = 10;
    public static final int SPRING = 20;
    public static final int SUMMER = 30;
    public static final int FALL   = 40;

    private long mId = -1;

    private int mCrn;

    private String mDepartment;

    private int mCourseNumber;

    private String mName;

    private Instructor mInstructor;

    private Schedule mSchedule;

    private int mCapacity;

    private int mEnrolled;

    private int mCredits;

    private int mTerm;

    public Course() {

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public int getCrn() {
        return mCrn;
    }

    public void setCrn(int crn) {
        this.mCrn = crn;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        this.mDepartment = department;
    }

    public int getCourseNumber() {
        return mCourseNumber;
    }

    public void setCourseNumber(int coursenumber) {
        this.mCourseNumber = coursenumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Instructor getInstructor() {
        return mInstructor;
    }

    public void setInstructor(Instructor instructor) {
        this.mInstructor = instructor;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule(Schedule schedule) {
        this.mSchedule = schedule;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public void setCapacity(int capacity) {
        this.mCapacity = capacity;
    }

    public int getEnrolled() {
        return mEnrolled;
    }

    public void setEnrolled(int enrolled) {
        this.mEnrolled = enrolled;
    }

    public int getCredits() {
        return mCredits;
    }

    public void setCredits(int credits) {
        this.mCredits = credits;
    }

    public int getTerm() {
        return mTerm;
    }

    public void setTerm(int term) {
        this.mTerm = term;
    }

    public ContentValues asContentValues() {
        return asContentValues(new ContentValues());
    }

    public ContentValues asContentValues(ContentValues values) {

        values.put(CourseContract._ID, mId);
        values.put(CourseContract.CRN, mCrn);
        values.put(CourseContract.DEPARTMENT, mDepartment);
        values.put(CourseContract.COURSENUMBER, mCourseNumber);
        values.put(CourseContract.NAME, mName);
        values.put(CourseContract.INSTRUCTOR, mInstructor.getId());
        //mInstructor.asContentValues(values);
        values.put(CourseContract.SCHEDULE,
                   mSchedule != null ? mSchedule.asString() : "");
        values.put(CourseContract.CAPACITY, mCapacity);
        values.put(CourseContract.ENROLLED, mEnrolled);
        values.put(CourseContract.CREDITS, mCredits);
        values.put(CourseContract.TERM, mTerm);

        return values;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Course)) {
            return false;
        }

        Course oCourse = (Course) obj;
        return new EqualsBuilder()
            .append(mId, oCourse.mId)
            .append(mCrn, oCourse.mCrn)
            .append(mDepartment, oCourse.mDepartment)
            .append(mCourseNumber, oCourse.mCourseNumber)
            .append(mName, oCourse.mName)
            .append(mInstructor, oCourse.mInstructor)
            .append(mSchedule, oCourse.mSchedule)
            .append(mCapacity, oCourse.mCapacity)
            .append(mEnrolled, oCourse.mEnrolled)
            .append(mCredits, oCourse.mCredits)
            .append(mTerm, oCourse.mTerm)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(mId)
            .append(mCrn)
            .append(mDepartment)
            .append(mCourseNumber)
            .append(mName)
            .append(mInstructor)
            .append(mSchedule)
            .append(mCapacity)
            .append(mEnrolled)
            .append(mCredits)
            .append(mTerm)
            .toHashCode();
    }

    public static Course fromCursor(Cursor cursor) {
        Course course = new Course();
        int col;

        col = cursor.getColumnIndex(CourseContract._ID);
        if (col != -1)
            course.setId(cursor.getLong(col));

        col = cursor.getColumnIndex(CourseContract.CRN);
        if (col != -1)
            course.setCrn(cursor.getInt(col));

        col = cursor.getColumnIndex(CourseContract.DEPARTMENT);
        if (col != -1)
            course.setDepartment(cursor.getString(col));

        col = cursor.getColumnIndex(CourseContract.COURSENUMBER);
        if (col != -1)
            course.setCourseNumber(cursor.getInt(col));

        col = cursor.getColumnIndex(CourseContract.NAME);
        if (col != -1)
            course.setName(cursor.getString(col));

        course.setInstructor(Instructor.fromCursor(cursor));

        col = cursor.getColumnIndex(CourseContract.SCHEDULE);
        if (col != -1)
            course.setSchedule(Schedule.fromString(cursor.getString(col)));

        col = cursor.getColumnIndex(CourseContract.CAPACITY);
        if (col != -1)
            course.setCapacity(cursor.getInt(col));

        col = cursor.getColumnIndex(CourseContract.ENROLLED);
        if (col != -1)
            course.setEnrolled(cursor.getInt(col));

        col = cursor.getColumnIndex(CourseContract.CREDITS);
        if (col != -1)
            course.setCredits(cursor.getInt(col));

        col = cursor.getColumnIndex(CourseContract.TERM);
        if (col != -1)
            course.setTerm(cursor.getInt(col));

        return course;
    }

}
