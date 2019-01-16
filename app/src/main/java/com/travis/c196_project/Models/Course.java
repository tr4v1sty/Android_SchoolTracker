package com.travis.c196_project.Models;

public class Course {

    private long courseId;
    private long termId;
    private String courseNotesText;
    private String courseMentorName;
    private String courseMentorPhone;
    private String courseMentorEmail;
    private int notificationStartDate;
    private int notificationEndDate;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;

    public Course(){ }

    @Override
    public String toString() {
        return courseName
                + "\n"
                + courseStart
                + " - "
                + courseEnd;
    }

    public long getCourseId() {
        return courseId;
    }

    public long getCourseTermId() {
        return termId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public int getNotificationStartDate() {
        return notificationStartDate;
    }

    public int getNotificationEndDate() {
        return notificationEndDate;
    }

    public String getCourseNotesText() {
        return courseNotesText;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setNotificationEndDate(int notificationEndDate) {
        this.notificationEndDate = notificationEndDate;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setNotificationStartDate(int notificationStartDate) {
        this.notificationStartDate = notificationStartDate;
    }

    public void setCourseNotesText(String courseNotesText) {
        this.courseNotesText = courseNotesText;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }
}
