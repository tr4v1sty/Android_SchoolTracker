package com.travis.c196_project;

public class Assessment {

    private long assessmentId;
    private long courseId;
    private String assessmentName;
    private String assessmentType;
    private String assessmentGoalDate;
    private int assessmentNotification;

    Assessment() {
    }

    @Override
    public String toString() {
        return assessmentName + "\n Date "
                + assessmentGoalDate
                + "\n Type: "
                + assessmentType;
    }

    long getAssessmentId() {
        return assessmentId;
    }

    long getCourseId() {
        return courseId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    String getAssessmentType() {
        return assessmentType;
    }

    String getAssessmentGoalDate() {
        return assessmentGoalDate;
    }

    int getAssessmentNotification() {
        return assessmentNotification;
    }

    void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    void setAssessmentGoalDate(String assessmentGoalDate) {
        this.assessmentGoalDate = assessmentGoalDate;
    }
}
