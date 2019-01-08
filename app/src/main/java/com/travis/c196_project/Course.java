package com.travis.c196_project;

public class Course {

    private long courseId;
    private long termId;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private int courseNotificationStart;
    private int courseNotificationEnd;private String courseNotesTitle;
    private String courseNotesText;
    private String courseMentorName;
    private String courseMentorPhone;
    private String courseMentorEmail;

    public Course (){

    }

    @Override
    public String toString() {
        return "Course Name: " + courseName + "\n Start: " + courseStart + "\n End: " + courseEnd;
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

    public int getCourseNotificationStart() {
        return courseNotificationStart;
    }

    public int getCourseNotificationEnd() {
        return courseNotificationEnd;
    }

    public String getCourseNotesTitle() {
        return courseNotesTitle;
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

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public void setCourseNotificationStart(int courseNotificationStart) {
        this.courseNotificationStart = courseNotificationStart;
    }

    public void setCourseNotificationEnd(int courseNotificationEnd) {
        this.courseNotificationEnd = courseNotificationEnd;
    }

    public void setCourseNotesTitle(String courseNotesTitle) {
        this.courseNotesTitle = courseNotesTitle;
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









}
