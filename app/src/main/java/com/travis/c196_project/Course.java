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

    Course(){

    }

    @Override
    public String toString() {
        return courseName
                + "\n"
                + courseStart
                + " - "
                + courseEnd;
    }

    long getCourseId() {
        return courseId;
    }

    long getCourseTermId() {
        return termId;
    }

    public String getCourseName() {
        return courseName;
    }

    String getCourseStart() {
        return courseStart;
    }

    String getCourseEnd() {
        return courseEnd;
    }

    String getCourseStatus() {
        return courseStatus;
    }

    int getCourseNotificationStart() {
        return courseNotificationStart;
    }

    int getCourseNotificationEnd() {
        return courseNotificationEnd;
    }

    String getCourseNotesTitle() {
        return courseNotesTitle;
    }

    String getCourseNotesText() {
        return courseNotesText;
    }

    String getCourseMentorName() {
        return courseMentorName;
    }

    String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    void setTermId(long termId) {
        this.termId = termId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    void setCourseNotificationStart(int courseNotificationStart) {
        this.courseNotificationStart = courseNotificationStart;
    }

    void setCourseNotificationEnd(int courseNotificationEnd) {
        this.courseNotificationEnd = courseNotificationEnd;
    }

    void setCourseNotesTitle(String courseNotesTitle) {
        this.courseNotesTitle = courseNotesTitle;
    }

    void setCourseNotesText(String courseNotesText) {
        this.courseNotesText = courseNotesText;
    }

    void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

}
